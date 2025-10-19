package matt.pas.myflp.domain.product;

import matt.pas.myflp.domain.product.dto.ProductDto;
import matt.pas.myflp.domain.product.dto.ProductForUserDto;
import matt.pas.myflp.domain.product.dto.ProductOrderDto;
import matt.pas.myflp.domain.product.dto.ProductToSaveDto;

import java.math.BigDecimal;

public class ProductMapper {


    public static ProductForUserDto mapToProductForUserDto(Product product, BigDecimal userPrice) {
        return new ProductForUserDto(
                product.getId(),
                product.getPartNumber(),
                product.getName(),
                product.getRetailPriceNetto(),
                product.getRetailPriceBrutto(),
                product.getCc(),
                userPrice
        );
    }

    public static ProductOrderDto mapToProductOrder(Product product, BigDecimal userPrice) {
        return new ProductOrderDto(
                product.getId(),
                product.getName(),
                product.getRetailPriceBrutto(),
                userPrice,
                product.getCc()
        );
    }
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName()
        );
    }

    public static Product mapProductToSaveToProduct(ProductToSaveDto productToSaveDto) {
        return new Product(
                productToSaveDto.getPartNumber(),
                productToSaveDto.getName(),
                productToSaveDto.getRetailPriceNetto(),
                productToSaveDto.getRetailPriceBrutto(),
                productToSaveDto.getCc(),
                productToSaveDto.getVat()
        );
    }
}
