package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.order.dto.OrderDto;
import matt.pas.myflp.domain.order.dto.OrderSummaryForProductDto;
import matt.pas.myflp.domain.order.dto.OrdersSummaryDto;
import matt.pas.myflp.domain.orderItem.OrderItem;
import matt.pas.myflp.domain.orderItem.OrderItemMapper;
import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.domain.user.UserService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderSummaryService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderSummaryService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }


    public OrdersSummaryDto getOrdersSummary(List<OrderDto> orders) {
        BigDecimal totalPrice = getTotalPriceForOrders(orders);
        BigDecimal totalProfit = getTotalProfitForOrders(orders);
        double totalCc = getTotalCcForOrders(orders);

        return new OrdersSummaryDto(totalPrice, totalProfit, totalCc);
    }

    private BigDecimal getTotalPriceForOrders(List<OrderDto> orders) {
        return orders.stream()
                .map(OrderDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getTotalProfitForOrders(List<OrderDto> orders) {
        return orders.stream()
                .map(OrderDto::getTotalProfit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private double getTotalCcForOrders(List<OrderDto> orders) {
        return orders.stream()
                .map(OrderDto::getTotalCc)
                .reduce(0.0, Double::sum);
    }

    public OrderSummaryForProductDto getSummaryForProduct(long productId){
        int quantity = getTotalQuantityForProduct(productId);

        final List<OrderItemDto> orderItemsDto = getOrderItemsForProduct(productId).stream()
                .map(OrderItemMapper::mapToDto)
                .toList();

        final BigDecimal totalProfit = getTotalProfitForProduct(orderItemsDto);
        final BigDecimal totalPrice = getTotalPriceForProduct(orderItemsDto);
        final double totalCc = getTotalCcForProduct(orderItemsDto);

        return new OrderSummaryForProductDto(totalPrice, totalProfit, totalCc, quantity);
    }

    private static double getTotalCcForProduct(List<OrderItemDto> orderItemsDto) {
        return orderItemsDto.stream()
                .map(OrderItemDto::getCc)
                .reduce(0.0, Double::sum);
    }

    private static BigDecimal getTotalPriceForProduct(List<OrderItemDto> orderItemsDto) {
        return orderItemsDto.stream()
                .map(OrderItemDto::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static BigDecimal getTotalProfitForProduct(List<OrderItemDto> orderItemsDto) {
        return orderItemsDto.stream()
                .map(OrderItemDto::getProfit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private int getTotalQuantityForProduct(long productId) {
        return getOrderItemsForProduct(productId).stream()
                .map(OrderItem::getQuantity)
                .reduce(0, Integer::sum);
    }

    private List<OrderItem> getOrderItemsForProduct(long productId) {
        final User user = userService.getCurrentUser();
        return orderRepository.findAllByUserAndItems_Product_Id(user, productId).stream()
                .map(Order::getItems)
                .flatMap(List::stream)
                .filter(item -> item.getProduct().getId() == productId)
                .toList();
    }

}
