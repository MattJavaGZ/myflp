package matt.pas.myflp.domain.order.dto;

import matt.pas.myflp.domain.orderItem.dto.OrderItemDto;
import matt.pas.myflp.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto implements Comparable<OrderDto> {

    private Long id;
    private User user;
    private String clientFirstName;
    private String clientLastName;
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
    private BigDecimal totalProfit;
    private double totalCc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getTotalCc() {
        return totalCc;
    }

    public void setTotalCc(double totalCc) {
        this.totalCc = totalCc;
    }
    @Override
    public int compareTo(OrderDto o) {
        return o.orderDate.compareTo(orderDate);
    }

}
