package matt.pas.myflp.domain.product.dto;

import java.math.BigDecimal;

public class ProductOrderDto {

    private Long id;
    private String name;
    private BigDecimal retailPriceBrutto;
    private BigDecimal purchasePriceBrutto;
    private double cc;


    public ProductOrderDto(Long id, String name, BigDecimal retailPriceBrutto, BigDecimal purchasePriceBrutto, double cc) {
        this.id = id;
        this.name = name;
        this.retailPriceBrutto = retailPriceBrutto;
        this.purchasePriceBrutto = purchasePriceBrutto;
        this.cc = cc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRetailPriceBrutto() {
        return retailPriceBrutto;
    }

    public void setRetailPriceBrutto(BigDecimal retailPriceBrutto) {
        this.retailPriceBrutto = retailPriceBrutto;
    }

    public BigDecimal getPurchasePriceBrutto() {
        return purchasePriceBrutto;
    }

    public void setPurchasePriceBrutto(BigDecimal purchasePriceBrutto) {
        this.purchasePriceBrutto = purchasePriceBrutto;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }
}
