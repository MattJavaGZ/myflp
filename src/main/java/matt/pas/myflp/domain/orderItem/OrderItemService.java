package matt.pas.myflp.domain.orderItem;

import matt.pas.myflp.domain.order.Order;
import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;
import matt.pas.myflp.domain.orderItem.dto.OrderItemToSaveDto;
import matt.pas.myflp.domain.product.Product;
import matt.pas.myflp.domain.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service

public class OrderItemService {


    private final ProductService productService;

    public OrderItemService(ProductService productService) {
        this.productService = productService;
    }

    public List<OrderItemDto> getOrderItemsDtoForOrder (Order order) {
       return order.getItems().stream()
                .map(OrderItemMapper::mapToDto)
                .toList();
    }

    public List<OrderItem> getOrderItemsWithToSaveDto(List<OrderItemToSaveDto> itemsDto) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemToSaveDto orderItemToSaveDto : itemsDto) {
            final OrderItem orderItemToSave = getOrderItem(orderItemToSaveDto);
            orderItems.add(orderItemToSave);
        }
        return orderItems;
    }

    private OrderItem getOrderItem (OrderItemToSaveDto orderItemToSaveDto) {
        final OrderItem orderItemToSave = new OrderItem();

        final Product product = productService.findById(orderItemToSaveDto.getProductId()).orElseThrow(
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

}
