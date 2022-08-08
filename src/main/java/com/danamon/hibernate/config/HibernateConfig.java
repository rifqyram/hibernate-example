package com.danamon.hibernate.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.username", System.getenv("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
            sessionFactory = configuration.configure("Hibernate.cfg.xml").buildSessionFactory();
        }
        return sessionFactory;
    }
}
