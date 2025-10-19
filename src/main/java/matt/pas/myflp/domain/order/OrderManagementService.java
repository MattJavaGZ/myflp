package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.client.Client;
import matt.pas.myflp.domain.client.ClientService;
import matt.pas.myflp.domain.orderItem.OrderItem;
import matt.pas.myflp.domain.orderItem.OrderItemService;
import matt.pas.myflp.domain.orderItem.dto.OrderItemToSaveDto;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.domain.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderManagementService {

    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final UserService userService;
    private final ClientService clientService;

    public OrderManagementService(OrderRepository orderRepository, OrderItemService orderItemService, UserService userService,
                                  ClientService clientService) {
        this.orderRepository = orderRepository;
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.clientService = clientService;
    }

    public Long saveOrder(long clientId, List<OrderItemToSaveDto> items) {
        final List<OrderItem> orderItems = orderItemService.getOrderItemsWithToSaveDto(items);
        final User user = userService.getCurrentUser();
        final Client client = clientService.findClientById(clientId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

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

    public void deleteOrderById(long id) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final User currentUser = userService.getCurrentUser();

        if (currentUser.equals(order.getUser())) {
            orderRepository.delete(order);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}


