package matt.pas.myflp.domain.orderItem;

import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;

import java.math.BigDecimal;

public class OrderItemMapper {

    public static OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductPartNumber(orderItem.getProduct().getPartNumber());
        orderItemDto.setProductName(orderItem.getProduct().getName());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPricePerPiece(orderItem.getPurchasePricePerPiece());
        orderItemDto.setTotalPrice(orderItem.getPurchasePricePerPiece().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
        orderItemDto.setProfit(orderItem.getProfit());
        orderItemDto.setCc(orderItem.getProduct().getCc() * orderItem.getQuantity());

        return orderItemDto;
    }
}
