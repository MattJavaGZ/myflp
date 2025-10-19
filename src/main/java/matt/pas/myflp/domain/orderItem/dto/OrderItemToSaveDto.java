package matt.pas.myflp.domain.orderItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class OrderItemToSaveDto {
    @JsonProperty("id")
    private Long productId;
    @JsonProperty("name")
    private String productName;
    private BigDecimal retailPriceBrutto;   // cena detaliczna za szt (do wyliczenia zysku)
    private Integer quantity;           // ile sztuk klient chce kupić
    private Integer discount;
    @JsonProperty("purchasePriceBrutto")// 0 / 5 / 10 / 15
    private BigDecimal purchasePriceBruttoPerPiece;  //cena zakupu usera za szt
    private double cc;



    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getRetailPriceBrutto() {
        return retailPriceBrutto;
    }

    public void setRetailPriceBrutto(BigDecimal retailPriceBrutto) {
        this.retailPriceBrutto = retailPriceBrutto;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public BigDecimal getPurchasePriceBruttoPerPiece() {
        return purchasePriceBruttoPerPiece;
    }

    public void setPurchasePriceBruttoPerPiece(BigDecimal purchasePriceBruttoPerPiece) {
        this.purchasePriceBruttoPerPiece = purchasePriceBruttoPerPiece;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", retailPriceBrutto=" + retailPriceBrutto +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", purchasePriceBrutto=" + purchasePriceBruttoPerPiece +
                ", cc=" + cc +
                '}';
    }
}
