package matt.pas.myflp.domain.product;

import matt.pas.myflp.domain.product.dto.ProductForUserDto;
import matt.pas.myflp.domain.product.dto.ProductOrderDto;
import matt.pas.myflp.domain.product.dto.ProductToSaveDto;
import matt.pas.myflp.domain.user.User;
import matt.pas.myflp.infrastructure.user.CurrentUserProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CurrentUserProvider currentUserProvider;

    public ProductService(ProductRepository productRepository, CurrentUserProvider currentUserProvider) {
        this.productRepository = productRepository;
        this.currentUserProvider = currentUserProvider;
    }

    public List<ProductForUserDto> getAllProductsForUser() {
        final User user = currentUserProvider.getCurrentUser();
        return productRepository.findAll().stream()
                .map(product -> getProductForUserDto(product, user))
                .sorted()
                .toList();
    }

    public List<ProductForUserDto> findProductsByUserWord (String userWord) {
        final User user = currentUserProvider.getCurrentUser();
        return productRepository.findAllByNameContainingIgnoreCaseOrPartNumberContainingIgnoreCase(userWord, userWord)
                .stream()
                .map(product -> getProductForUserDto(product, user))
                .toList();
    }

    public List<ProductOrderDto> findProductsByUserWordToOrderSelect (String userWord) {
        final User user = currentUserProvider.getCurrentUser();
        return productRepository.findAllByNameContainingIgnoreCaseOrPartNumberContainingIgnoreCase(userWord, userWord)
                .stream()
                .map(product -> getProductOrderDto(product, user))
                .toList();
    }

    private ProductForUserDto getProductForUserDto(Product product, User user) {
        final BigDecimal userPrice = getPurchasePrice(product, user);
        return ProductMapper.mapToProductForUserDto(product, userPrice);
    }

    private ProductOrderDto getProductOrderDto(Product product, User user) {
        final BigDecimal userPrice = getPurchasePrice(product, user);
        return ProductMapper.mapToProductOrder(product, userPrice);
    }

    private BigDecimal getPurchasePrice(Product product, User user) {
        final BigDecimal retailPriceBrutto = product.getRetailPriceBrutto();
        final double discount = user.getWorkStation().getDiscount();
        return retailPriceBrutto.multiply(
                BigDecimal.ONE.subtract(
                        BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
                )
        );
    }

    public String getNameForProductId(long id) {
        final Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return product.getName();
    }

    public void addNewProduct(ProductToSaveDto productToSave) {
        final Product product = ProductMapper.mapProductToSaveToProduct(productToSave);
        productRepository.save(product);
    }

}
