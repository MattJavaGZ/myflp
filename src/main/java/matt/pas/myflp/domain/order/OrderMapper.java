package matt.pas.myflp.domain.order;

import matt.pas.myflp.domain.order.dto.OrderDto;
import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;

import java.util.List;

public class OrderMapper {

    public static OrderDto mapToDto (Order order, List<OrderItemDto> items) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUser(order.getUser());

        orderDto.setClientFirstName(order.getClient() == null ? "Usunięty klient" : order.getClient().getFirstName());
        orderDto.setClientLastName(order.getClient() == null ? "" : order.getClient().getLastName());

        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setItems(items);
        orderDto.setTotalPrice(order.getTotalPurchasePrice());
        orderDto.setTotalProfit(order.getTotalProfit());
        orderDto.setTotalCc(order.getTotalCc());

        return orderDto;
    }
}
