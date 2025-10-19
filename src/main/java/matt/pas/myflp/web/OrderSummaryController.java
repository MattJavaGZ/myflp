package matt.pas.myflp.web;

import matt.pas.myflp.domain.order.OrderService;
import matt.pas.myflp.domain.order.OrderSummaryService;
import matt.pas.myflp.domain.order.dto.OrderDto;
import matt.pas.myflp.domain.order.dto.OrdersSummaryDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/zamowienia/podsumowanie")
public class OrderSummaryController {

    private final OrderService orderService;
    private final OrderSummaryService orderSummaryService;

    public OrderSummaryController(OrderService orderService, OrderSummaryService orderSummaryService) {
        this.orderService = orderService;
        this.orderSummaryService = orderSummaryService;
    }

    @GetMapping()
    String ordersSummaryForm(Model model) {
        model.addAttribute("heading", "Podsumowanie zamówień");
        return "orders-summary";
    }

    @GetMapping("/{summaryType}")
    String ordersSummary(Model model, @PathVariable String summaryType,
                         @RequestParam(required = false, defaultValue = "dateDown") String sort) {

        final List<OrderDto> orders = orderService.getOrdersForUserByDate(sort, summaryType);
        final OrdersSummaryDto ordersSummary = orderSummaryService.getOrdersSummary(orders);

        model.addAttribute("heading", "Podsumowanie zamówień");
        model.addAttribute("ordersSummary", ordersSummary);
        model.addAttribute("orders", orders);
        model.addAttribute("numberOfOrders", orders.size());
        model.addAttribute("range", summaryType);

        return "orders-summary";
    }

}
