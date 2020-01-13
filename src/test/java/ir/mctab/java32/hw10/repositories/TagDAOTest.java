package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Tag;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagDAOTest {
    Session session = Mockito.mock(Session.class);
    TagDAO tagDAO = new TagDAO();
    @Test
    void costumeTag()  {
        tagDAO.setSession(session);
        Tag tag = new Tag();
        String name = "reza";
        assertEquals("rambod",tagDAO.costumeTag("rambod",tag).getTagName());
    }
}