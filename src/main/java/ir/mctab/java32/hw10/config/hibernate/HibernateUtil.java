package ir.mctab.java32.hw10.config.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    static SessionFactory sessionFactory;
    static SessionFactory sessionFactory1;

    static {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        sessionFactory1 = new Configuration().configure("hibernate.second.cfg.xml").buildSessionFactory();
    }
    public static SessionFactory getSession(){
        return sessionFactory;
    }
    public static SessionFactory getSessionFactory1(){
        return sessionFactory1;
    }
}
