package matt.pas.myflp.domain.orderItem;

import jakarta.persistence.*;
import matt.pas.myflp.domain.order.Order;
import matt.pas.myflp.domain.product.Product;

import java.math.BigDecimal;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private int quantity;
    private BigDecimal purchasePricePerPiece; //cena zakupu za szt
    private BigDecimal profit; //


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePricePerPiece() {
        return purchasePricePerPiece;
    }

    public void setPurchasePricePerPiece(BigDecimal purchasePrice) {
        this.purchasePricePerPiece = purchasePrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}


