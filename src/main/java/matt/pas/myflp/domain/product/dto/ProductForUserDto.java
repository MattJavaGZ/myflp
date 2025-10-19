package matt.pas.myflp.domain.product.dto;

import java.math.BigDecimal;

public class ProductForUserDto implements Comparable<ProductForUserDto> {

    private Long id;
    private String partNumber;
    private String name;
    private BigDecimal retailPriceNetto;
    private BigDecimal retailPriceBrutto;
    private double cc;
    private BigDecimal purchasePriceBrutto;

    public ProductForUserDto(Long id, String partNumber, String name, BigDecimal retailPriceNetto,
                             BigDecimal retailPriceBrutto, double cc, BigDecimal purchasePriceBrutto) {
        this.id = id;
        this.partNumber = partNumber;
        this.name = name;
        this.retailPriceNetto = retailPriceNetto;
        this.retailPriceBrutto = retailPriceBrutto;
        this.cc = cc;
        this.purchasePriceBrutto = purchasePriceBrutto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRetailPriceNetto() {
        return retailPriceNetto;
    }

    public void setRetailPriceNetto(BigDecimal retailPriceNetto) {
        this.retailPriceNetto = retailPriceNetto;
    }

    public BigDecimal getRetailPriceBrutto() {
        return retailPriceBrutto;
    }

    public void setRetailPriceBrutto(BigDecimal retailPriceBrutto) {
        this.retailPriceBrutto = retailPriceBrutto;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }

    public BigDecimal getPurchasePriceBrutto() {
        return purchasePriceBrutto;
    }

    public void setPurchasePriceBrutto(BigDecimal purchasePriceBrutto) {
        this.purchasePriceBrutto = purchasePriceBrutto;
    }

    @Override
    public int compareTo(ProductForUserDto o) {
        return - o.name.compareTo(this.name);
    }
}
