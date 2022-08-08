package com.danamon.hibernate.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "trx_purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private Date transactionDate;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = PurchaseDetail.class, mappedBy = "purchase")
    private List<PurchaseDetail> purchaseDetails;

    public Purchase(Integer id, Customer customer, Date transactionDate, List<PurchaseDetail> purchaseDetails) {
        this.id = id;
        this.customer = customer;
        this.transactionDate = transactionDate;
        this.purchaseDetails = purchaseDetails;
    }

    public Purchase(Customer customer, Date transactionDate) {
        this.customer = customer;
        this.transactionDate = transactionDate;
    }



    public Purchase() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public List<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(List<PurchaseDetail> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customer=" + customer +
                ", transactionDate=" + transactionDate +
                ", purchaseDetails=" + purchaseDetails +
                '}';
    }
}
