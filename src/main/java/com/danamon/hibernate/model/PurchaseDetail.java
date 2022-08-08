package com.danamon.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "trx_purchase_detail")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Purchase.class)
    @JoinColumn(name = "purchase_id", referencedColumnName = "id")
    private Purchase purchase;

    public PurchaseDetail(Integer id, Product product, Integer productPrice, Integer quantity, Purchase purchase) {
        this.id = id;
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.purchase = purchase;
    }

    public PurchaseDetail(Product product, Integer productPrice, Integer quantity) {
        this.product = product;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public PurchaseDetail() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String toString() {
        return "PurchaseDetail{" +
                "id=" + id +
                ", product=" + product +
                ", productPrice=" + productPrice +
                ", quantity=" + quantity +
                ", purchase=" + purchase +
                '}';
    }
}
