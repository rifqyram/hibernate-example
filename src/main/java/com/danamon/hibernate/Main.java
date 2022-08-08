package com.danamon.hibernate;

import com.danamon.hibernate.dao.CustomerDao;
import com.danamon.hibernate.dao.ProductDao;
import com.danamon.hibernate.model.Customer;
import com.danamon.hibernate.model.Product;
import com.danamon.hibernate.model.PurchaseDetail;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CustomerDao customerDao = new CustomerDao();
        customerDao.save(new Customer("Fadli", "Jakarta", LocalDate.of(2005, Month.JUNE, 04), "081234", 1));

        // Pembeli
        Customer rifqi = customerDao.findById(2);

        // Product yang dibeli
//        ProductDao productDao = new ProductDao();
//        Product kacamata = productDao.findById(1);
//        Product topi = productDao.findById(2);
//        Product sepatu = productDao.findById(3);
//
//        PurchaseDetail buyKacamata = new PurchaseDetail(kacamata, kacamata.getPrice(), 1);
//        PurchaseDetail buyTopi = new PurchaseDetail(topi, topi.getPrice(), 1);
//        PurchaseDetail buySepatu = new PurchaseDetail(sepatu, sepatu.getPrice(), 1);
//
//        List<PurchaseDetail> purchaseDetails = Arrays.asList(buyKacamata, buyTopi, buySepatu);

        // Buat transaksi
//        TransactionDao transactionDao = new TransactionDao();
//        transactionDao.transaction(purchaseDetails, rifqi);

    }

}
