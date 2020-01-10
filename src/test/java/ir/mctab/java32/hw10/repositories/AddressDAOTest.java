package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.persistence.Query;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddressDAOTest {

    AddressDAO addressDAO = new AddressDAO();


    @Test
    void loadAddress() {
        User user = new User();
        user.setId(1L);
        assertEquals(1, addressDAO.loadAddress(user).getUserId());
    }

}