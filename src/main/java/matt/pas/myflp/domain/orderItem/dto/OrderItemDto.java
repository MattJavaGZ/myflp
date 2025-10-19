package matt.pas.myflp.domain.orderItem.dto;

import java.math.BigDecimal;

public class OrderItemDto {

    private String productPartNumber;
    private String productName;
    private int quantity;
    private BigDecimal pricePerPiece; //cena zakupu za szt
    private BigDecimal totalPrice;
    private BigDecimal profit; //
    private double cc;


    public String getProductPartNumber() {
        return productPartNumber;
    }

    public void setProductPartNumber(String productPartNumber) {
        this.productPartNumber = productPartNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerPiece() {
        return pricePerPiece;
    }

    public void setPricePerPiece(BigDecimal pricePerPiece) {
        this.pricePerPiece = pricePerPiece;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }
}
