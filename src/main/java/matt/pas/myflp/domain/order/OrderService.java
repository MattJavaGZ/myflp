package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.order.dto.OrderDto;
import matt.pas.myflp.domain.orderItem.OrderItemMapper;
import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.infrastructure.user.CurrentUserProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CurrentUserProvider currentUserProvider;

    public OrderService(OrderRepository orderRepository, CurrentUserProvider currentUserProvider) {
        this.orderRepository = orderRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<OrderDto> getOrdersForUser(String sort) {
        final User user = currentUserProvider.getCurrentUser();
        return orderRepository.findAllByUser(user).stream()
                .map(this::orderToOrderDto)
                .sorted(orderComparator(sort))
                .toList();
    }

    private Comparator<OrderDto> orderComparator(String sort) {
        return switch (sort) {
            case "dateUp" -> Comparator.comparing(OrderDto::getOrderDate);
            case "dateDown" -> (o1, o2) -> -o1.getOrderDate().compareTo(o2.getOrderDate());
            case "priceUp" -> Comparator.comparing(OrderDto::getTotalPrice);
            case "priceDown" -> (o1, o2) -> -o1.getTotalPrice().compareTo(o2.getTotalPrice());
            case "profitUp" -> Comparator.comparing(OrderDto::getTotalProfit);
            case "profitDown" ->  (o1, o2) -> -o1.getTotalProfit().compareTo(o2.getTotalProfit());
            default -> (o1, o2) -> -o1.getOrderDate().compareTo(o2.getOrderDate());
        };
    }

    public List<OrderDto> getOrdersForUserByDate(String sort, String dateRange) {
        final User user = currentUserProvider.getCurrentUser();
        final DateRangeForOrdersSummary dateRangeToOrdersSummary = OrderDateRangeCalculator.dateRangeToSummary(dateRange);
        final List<Order> orders = orderRepository.findAllByUserAndOrderDateBetween
                (user, dateRangeToOrdersSummary.start(), dateRangeToOrdersSummary.end());

        return mapToDtoAndSort(orders, sort);
    }

    public OrderDto getOrderById(Long id) {
        final Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final User user = currentUserProvider.getCurrentUser();

        if (!order.getUser().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        final List<OrderItemDto> orderItemsDto = getOrderItemsDtoForOrder(order);

        return OrderMapper.mapToDto(order, orderItemsDto);
    }


    public List<OrderDto> getOrdersForUserByClient(long clientId, String sort) {
        final User user = currentUserProvider.getCurrentUser();
        final List<Order> orders = orderRepository.findAllByUserAndClient_Id(user, clientId);

        return mapToDtoAndSort(orders, sort);
    }

    public List<OrderDto> getOrdersForUserByProduct(long productId, String sort) {
        final User user = currentUserProvider.getCurrentUser();
        final List<Order> orders = orderRepository.findAllByUserAndItems_Product_Id(user, productId);

        return mapToDtoAndSort(orders, sort);
    }

    private List<OrderDto> mapToDtoAndSort(List<Order> orders, String sort) {
        return orders.stream()
                .map(this::orderToOrderDto)
                .sorted(orderComparator(sort))
                .toList();
    }

    private OrderDto orderToOrderDto(Order order) {
        return OrderMapper.mapToDto(order, getOrderItemsDtoForOrder(order));
    }

    private List<OrderItemDto> getOrderItemsDtoForOrder (Order order) {
        return order.getItems().stream()
                .map(OrderItemMapper::mapToDto)
                .toList();
    }

}
