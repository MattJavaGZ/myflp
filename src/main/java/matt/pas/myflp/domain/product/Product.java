package matt.pas.myflp.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String partNumber;
    private String name;
    private BigDecimal retailPriceNetto;
    private BigDecimal retailPriceBrutto;
    private double cc;
    private Integer vat;

    public Product(String partNumber, String name, BigDecimal retailPriceNetto, BigDecimal retailPriceBrutto, double cc, Integer vat) {
        this.partNumber = partNumber;
        this.name = name;
        this.retailPriceNetto = retailPriceNetto;
        this.retailPriceBrutto = retailPriceBrutto;
        this.cc = cc;
        this.vat = vat;
    }

    public Product() {
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

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }
}
