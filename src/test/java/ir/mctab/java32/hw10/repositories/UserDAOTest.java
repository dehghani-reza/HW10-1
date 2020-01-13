package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Role;
import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    Session session = Mockito.mock(Session.class);
    UserDAO userDAO = new UserDAO();

    @Test
    void changePassword() {
        userDAO.setSession(session);
        User user = new User();
        user.setPassword("00");
        Mockito.when(session.load(User.class, user.getId())).thenReturn(user);
        userDAO.changePassword(user, "0");
        assertEquals("0", user.getPassword());
    }

    @Test
    void loadWriter() {
        userDAO.setSession(session);
        User user = new User();
        User user1 = new User();
        Role role = new Role("Writer");
        Role role1 = new Role("Admin");
        role.setId(1L);
        role1.setId(2L);
        user.setRoles(Collections.singleton(role));
        user1.setRoles(Collections.singleton(role1));
        List<User> users = new ArrayList<>() {{
            add(user);
            add(user1);
        }};
        Query<User> query = Mockito.mock(Query.class);
        Query<Role> query1 = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("From User")).thenReturn(query);
        Mockito.when(session.createQuery("From Role where  roleName = 'Writer' ")).thenReturn(query1);
        Mockito.when(query1.uniqueResult()).thenReturn(role);
        Mockito.when(query.list()).thenReturn(users);
        assertEquals(1, userDAO.loadWriter().size());
    }

    @Test
    void addRole() {
        userDAO.setSession(session);
        User user = new User();
        User user1 = new User();
        Long id = 1L;
        Long id1 = 2L;
        user.setId(id);
        user1.setId(id1);
        Role role = new Role("Writer");
        Role role1 = new Role("Admin");
        role.setId(1L);
        role1.setId(2L);
        user.getRoles().add(role);
        Mockito.when(session.load(User.class, id)).thenReturn(user);
        Query<Role> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("From Role where  roleName = 'Admin' ")).thenReturn(query);
        Mockito.when(query.uniqueResult()).thenReturn(role1);
        userDAO.addRole(1L);
        assertEquals (2,user.getRoles().size());
        userDAO.addRole(1L);
        assertEquals(1L,user.getRoles().size());


    }
}