package matt.pas.myflp.domain.product.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductToSaveDto {
    @NotEmpty(message = "Numer produktu nie może być pusty")
    private String partNumber;
    @NotEmpty(message = "Nazwa produktu nie może być pusta")
    private String name;
    @NotNull(message = "Cena produktu nie może być pusta")
    @DecimalMin(value = "1", message = "Cena musi być większa od zera")
    private BigDecimal retailPriceNetto;
    @NotNull(message = "Cena produktu nie może być pusta")
    @DecimalMin(value = "1", message = "Cena musi być większa od zera")
    private BigDecimal retailPriceBrutto;
    @NotNull(message = "Punkty CC nie mogą być puste")
    private double cc;
    @NotNull(message = "Musisz podać stawkę VAT")
    private Integer vat;


    public ProductToSaveDto() {
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

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }
}
