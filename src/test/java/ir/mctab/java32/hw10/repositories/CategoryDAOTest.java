package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Article;
import ir.mctab.java32.hw10.entities.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOTest {
    Session session = Mockito.mock(Session.class);
    CategoryDAO categoryDAO = new CategoryDAO();

    @Test
    void addCategory() throws Exception {
        categoryDAO.setSession(session);
        String title = "reza";
        String description = "reza";
        Query<Category> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("From Category where title = '" + title + "' and description = '" + description + "'"))
                .thenReturn(query);
        Category category = new Category();
        category.setDescription(description);
        category.setTitle(title);
        List<Category> categoryList = new ArrayList<>();
        Mockito.when(query.list()).thenReturn(categoryList);
        assertEquals(category, categoryDAO.addCategory(title, description));
    }

    @Test
    void loadCategory() {
        categoryDAO.setSession(session);
        Category category = new Category();
        category.setId(1L);
        Long categoryId = 1L;
        Query<Category> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("FROM Category where id = " + categoryId)).thenReturn(query);
        Mockito.when(query.uniqueResult()).thenReturn(category);
       assertEquals(category,categoryDAO.loadCategory(categoryId));

    }
}