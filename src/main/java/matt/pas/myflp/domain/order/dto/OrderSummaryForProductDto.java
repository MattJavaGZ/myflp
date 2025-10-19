package matt.pas.myflp.domain.order.dto;

import java.math.BigDecimal;

public class OrderSummaryForProductDto {

    private BigDecimal totalPrice;
    private BigDecimal totalProfit;
    private double totalCc;
    private int totalQuantity;

    public OrderSummaryForProductDto(BigDecimal totalPrice, BigDecimal totalProfit, double totalCc, int totalQuantity) {
        this.totalPrice = totalPrice;
        this.totalProfit = totalProfit;
        this.totalCc = totalCc;
        this.totalQuantity = totalQuantity;
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

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
