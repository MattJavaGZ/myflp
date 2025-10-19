package matt.pas.myflp.domain.order.dto;

import java.math.BigDecimal;

public class OrdersSummaryDto {

    private BigDecimal totalPrice;
    private BigDecimal totalProfit;
    private double totalCc;

    public OrdersSummaryDto(BigDecimal totalPrice, BigDecimal totalProfit, double totalCc) {
        this.totalPrice = totalPrice;
        this.totalProfit = totalProfit;
        this.totalCc = totalCc;
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
}
