package com.danamon.hibernate.dao;

import com.danamon.hibernate.config.HibernateConfig;
import com.danamon.hibernate.model.Customer;
import com.danamon.hibernate.model.Product;
import com.danamon.hibernate.model.Purchase;
import com.danamon.hibernate.model.PurchaseDetail;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TransactionDao {

    public void transaction(List<PurchaseDetail> purchaseDetails, Customer customer) {
        Session session = HibernateConfig.getSessionFactory().openSession();

        try {
            session.beginTransaction();

            /* Lakukan Pembelian - Transaction Purchase untuk membentuk/membuat id trx_purchase */
            Purchase purchase = new Purchase(customer, new Date());
            Serializable id = session.save(purchase);
            Purchase savedPurchase = session.get(Purchase.class, id);

            /* Karena bisa melakukan banyak pembelian maka purchase detail di loop
            untuk mendapatkan tiap detail product yang dibeli */
            for (PurchaseDetail purchaseDetail : purchaseDetails) {
                /* set relasi Purchase id di table trx_purchase_detail untuk mengisi id purchase */
                purchaseDetail.setPurchase(savedPurchase);

                /* Logic untuk mengurangi stock saat melakukan transaksi */
                Integer currentStock = purchaseDetail.getProduct().getStock();
                if (currentStock <= 0) throw new RuntimeException("Product sold out");
                purchaseDetail.getProduct().setStock(currentStock - purchaseDetail.getQuantity());

                /* Update Product */
                session.update(purchaseDetail.getProduct());

                /* Insert/Create purchase detail */
                session.save(purchaseDetail);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e.getMessage());
        } finally {
            session.close();
        }
    }
}
