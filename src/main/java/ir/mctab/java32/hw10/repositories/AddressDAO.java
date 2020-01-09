package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw10.entities.Address;
import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Session;

import javax.persistence.Query;

public class AddressDAO {


    public void createAddress(Long zipCode, String city, String street, String alley, User user) {
        Address address = new Address(zipCode, city, street, alley, user.getId());
        try (Session session = HibernateUtil.getSessionFactory1().openSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }

    public Address loadAddress(User user) {
        Long userId = user.getId();
        Address address;
        try (Session session = HibernateUtil.getSessionFactory1().openSession()) {
            Query query = session.createQuery("from  Address where userId = "+userId);
            address =(Address) query.getSingleResult();
        }
        return address;
    }

    public void updateAddress(Long zipCode, String city, String street, String alley, User user){
        Address address = loadAddress(user);
        address.setZipCode(zipCode);
        address.setCity(city);
        address.setStreet(street);
        address.setAlley(alley);
        try(Session session = HibernateUtil.getSessionFactory1().openSession()){
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        }
    }

    public void deleteAddress(User user){
        Address address = loadAddress(user);
        try(Session session = HibernateUtil.getSessionFactory1().openSession()){
            session.beginTransaction();
           session.delete(address);
           session.getTransaction().commit();
        }
    }

}
