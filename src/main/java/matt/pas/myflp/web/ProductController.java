package matt.pas.myflp.web;

import jakarta.validation.Valid;
import matt.pas.myflp.domain.product.ProductService;
import matt.pas.myflp.domain.product.dto.ProductForUserDto;
import matt.pas.myflp.domain.product.dto.ProductOrderDto;
import matt.pas.myflp.domain.product.dto.ProductToSaveDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produkty")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    String home(Model model) {
        final List<ProductForUserDto> allProducts = productService.getAllProductsForUser();
        model.addAttribute("products", allProducts);
        return "products-list";
    }

    @GetMapping("/szukaj")
    String productSearch(Model model, @RequestParam String productSearch) {
        final List<ProductForUserDto> products = productService.findProductsByUserWord(productSearch);
        model.addAttribute("products", products);
        return "products-list";
    }
    @GetMapping("/szukaj-podpowiedz")
    @ResponseBody
    List<ProductOrderDto> productSearchToSelect(@RequestParam String word) {
        return productService.findProductsByUserWordToOrderSelect(word);
    }

    @GetMapping("/dodaj")
    String productAddForm (Model model) {
        final ProductToSaveDto productToSave = new ProductToSaveDto();
        model.addAttribute("productToSave", productToSave);
        return "product-add-form";
    }

    @PostMapping("/dodaj")
    String productAdd(@Valid @ModelAttribute("productToSave") ProductToSaveDto productToSave, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-add-form";
        } else {
            productService.addNewProduct(productToSave);
            return "redirect:/";
        }
    }

}
