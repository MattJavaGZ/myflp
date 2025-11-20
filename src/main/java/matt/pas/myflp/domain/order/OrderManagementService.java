package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.client.Client;
import matt.pas.myflp.domain.client.ClientRepository;
import matt.pas.myflp.domain.orderItem.OrderItem;
import matt.pas.myflp.domain.orderItem.dto.OrderItemToSaveDto;
import matt.pas.myflp.domain.product.Product;
import matt.pas.myflp.domain.product.ProductRepository;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.infrastructure.user.CurrentUserProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagementService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;

    public OrderManagementService(OrderRepository orderRepository, ClientRepository clientRepository,
                                  ProductRepository productRepository, CurrentUserProvider currentUserProvider) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public Long saveOrder(long clientId, List<OrderItemToSaveDto> items) {
        final List<OrderItem> orderItems = getOrderItemsWithToSaveDto(items);
        final User user = currentUserProvider.getCurrentUser();
        final Client client = clientRepository.findById(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        final Order order = new Order();
        order.setUser(user);
        order.setClient(client);
        order.setOrderDate(LocalDateTime.now());

        for (OrderItem orderItem : orderItems) {
            order.addItem(orderItem);
        }
        order.setTotalPurchasePrice(getTotalPurchasePrice(orderItems)); // ogolna cena zakupu
        order.setTotalProfit(getTotalProfit(orderItems)); //ogólny zysk
        order.setTotalCc(getTotalCc(orderItems)); //ogólna ilość cc
        final Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }


    private BigDecimal getTotalPurchasePrice(List<OrderItem> items) {
        return items.stream()
                .map(item -> BigDecimal.valueOf(item.getQuantity()).multiply(item.getPurchasePricePerPiece()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalProfit(List<OrderItem> items) {
        return items.stream()
                .map(OrderItem::getProfit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private double getTotalCc(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getQuantity() * item.getProduct().getCc())
                .reduce(0.0, Double::sum);
    }

    private List<OrderItem> getOrderItemsWithToSaveDto(List<OrderItemToSaveDto> itemsDto) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemToSaveDto orderItemToSaveDto : itemsDto) {
            final OrderItem orderItemToSave = getOrderItem(orderItemToSaveDto);
            orderItems.add(orderItemToSave);
        }
        return orderItems;
    }

    private OrderItem getOrderItem (OrderItemToSaveDto orderItemToSaveDto) {
        final OrderItem orderItemToSave = new OrderItem();

        final Product product = productRepository.findById(orderItemToSaveDto.getProductId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderItemToSave.setProduct(product);
        orderItemToSave.setQuantity(orderItemToSaveDto.getQuantity());
        orderItemToSave.setPurchasePricePerPiece(getDiscountedPricePerPiece(orderItemToSaveDto));
        orderItemToSave.setProfit(getOrderItemProfit(orderItemToSaveDto));

        return orderItemToSave;
    }

    private BigDecimal getOrderItemProfit(OrderItemToSaveDto orderItemToSaveDto) {

        final BigDecimal discountedPricePerPiece = getDiscountedPricePerPiece(orderItemToSaveDto);

        final BigDecimal quality = BigDecimal.valueOf(orderItemToSaveDto.getQuantity());

        return discountedPricePerPiece.multiply(
                quality).setScale(4, RoundingMode.HALF_UP).subtract(
                orderItemToSaveDto.getPurchasePriceBruttoPerPiece().multiply(quality).setScale(4,RoundingMode.HALF_UP)
        );
    }

    private BigDecimal getDiscountedPricePerPiece(OrderItemToSaveDto dto) {
        return dto.getRetailPriceBrutto()
                .multiply(BigDecimal.ONE.subtract(
                        BigDecimal.valueOf(dto.getDiscount()).divide(BigDecimal.valueOf(100),4, RoundingMode.HALF_UP)))
                .setScale(4, RoundingMode.HALF_UP);
    }

    public void deleteOrderById(long id) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final User currentUser = currentUserProvider.getCurrentUser();

        if (currentUser.equals(order.getUser())) {
            orderRepository.delete(order);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}


