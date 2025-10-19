package matt.pas.myflp.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import matt.pas.myflp.domain.client.ClientService;
import matt.pas.myflp.domain.client.dto.ClientDto;
import matt.pas.myflp.domain.order.OrderManagementService;
import matt.pas.myflp.domain.order.OrderService;
import matt.pas.myflp.domain.order.OrderSummaryService;
import matt.pas.myflp.domain.order.dto.OrderDto;
import matt.pas.myflp.domain.order.dto.OrderSummaryForProductDto;
import matt.pas.myflp.domain.order.dto.OrdersSummaryDto;
import matt.pas.myflp.domain.orderItem.dto.OrderItemToSaveDto;
import matt.pas.myflp.domain.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/zamowienia")
public class OrderController {

    private final ObjectMapper jacksonObjectMapper;
    private final ClientService clientService;
    private final OrderService orderService;
    private final OrderSummaryService orderSummaryService;
    private final OrderManagementService orderSaveService;
    private final ProductService productService;
    private final OrderManagementService orderManagementService;

    public OrderController(ObjectMapper jacksonObjectMapper, ClientService clientService, OrderService orderService, OrderSummaryService orderSummaryService, OrderManagementService orderSaveService,
                           ProductService productService, OrderManagementService orderManagementService) {
        this.jacksonObjectMapper = jacksonObjectMapper;
        this.clientService = clientService;
        this.orderService = orderService;
        this.orderSummaryService = orderSummaryService;
        this.orderSaveService = orderSaveService;
        this.productService = productService;
        this.orderManagementService = orderManagementService;
    }

    @GetMapping()
    String getUserOrders(Model model, @RequestParam(required = false, defaultValue = "dateDown") String sort) {
        final List<OrderDto> orders = orderService.getOrdersForUser(sort);
        model.addAttribute("heading", "Lista wszystkich Twoich zamówień");
        model.addAttribute("orders", orders);
        return "orders-list";
    }

    @GetMapping("/{id}")
    String getOrder(@PathVariable long id, Model model) {
        final OrderDto order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order";
    }

    @GetMapping("/klient/{idClient}")
    String getOrdersByClient(@PathVariable long idClient, Model model,
                             @RequestParam(required = false, defaultValue = "dateDown") String sort) {
        final List<OrderDto> orders = orderService.getOrdersForUserByClient(idClient, sort);

        if (!clientService.verifiClient(idClient)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        final String clientDignity = clientService.getFirstAndLastNameClient(idClient);
        final OrdersSummaryDto ordersSummary = orderSummaryService.getOrdersSummary(orders);

        model.addAttribute("heading", "Podsumowanie zamówień dla " + clientDignity);
        model.addAttribute("orders", orders);
        model.addAttribute("ordersSummary", ordersSummary);

        return "orders-list";
    }

    @GetMapping("/produkt/{idProduct}")
    String getOrdersByProduct(@PathVariable long idProduct, Model model,
                              @RequestParam(required = false, defaultValue = "dateDown") String sort) {
        final List<OrderDto> orders = orderService.getOrdersForUserByProduct(idProduct, sort);
        final String productName = productService.getNameForProductId(idProduct);
        final OrderSummaryForProductDto summaryForProduct = orderSummaryService.getSummaryForProduct(idProduct);

        model.addAttribute("heading", "Podsumowanie produktu: " + productName);
        model.addAttribute("orders", orders);
        model.addAttribute("summaryForProduct", summaryForProduct);

        return "orders-list";
    }

    @GetMapping("/nowe")
    public String newOrder(@RequestParam Long idClient, Model model) {
        final ClientDto client = clientService.findClientDtoById(idClient).orElseThrow();
        model.addAttribute("client", client);
        return "order-form";
    }

    @PostMapping("/zapisz")
    public String saveOrder(@RequestParam Long clientId, @RequestParam String orderItemsJson,
                            RedirectAttributes redirectAttributes) {
        try {
            final List<OrderItemToSaveDto> items = jacksonObjectMapper.readValue(orderItemsJson, new TypeReference<List<OrderItemToSaveDto>>() {
            });

            if (items.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Nie dodano żadnych produktów do zamówienia.");
                return "redirect:/zamowienia/nowe?clientId=" + clientId;
            }

            final Long orderId = orderSaveService.saveOrder(clientId, items);
            redirectAttributes.addFlashAttribute("success", "Zamówienie zostało zapisane.");
            return "redirect:/zamowienia/" + orderId;

        } catch (JsonProcessingException e) {
            redirectAttributes.addFlashAttribute("error", "Błąd przy przetwarzaniu produktów.");
            return "redirect:/zamowienia/nowe?clientId=" + clientId;
        }
    }

    @GetMapping("/usun/{id}")
    String deleteOrderById(@PathVariable long id, RedirectAttributes redirectAttributes) {
        orderManagementService.deleteOrderById(id);
        redirectAttributes.addFlashAttribute(RegisterController.NOTIFICATION_ATTRIBUTE, "Zamówienie zostało usunięte");

        return "redirect:/zamowienia";
    }

}


