package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Category;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class CategoryDAO {

    Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public List<Category> categoryList() {
        Query<Category> query3 = session.createQuery("From Category ");
        List<Category> categories = query3.list();
        return categories;
    }

    public Category addCategory(String title , String description) throws Exception{
        Query<Category> query1 = session.createQuery("From Category where title = '"+title+"' and description = '"+description+"'");
        List<Category> categories = query1.list();
        if(!categories.isEmpty()){
            throw new Exception("this category is already exist");
        }
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        session.save(category);
        return category;
    }

    public Category loadCategory(Long categoryId){
        Query<Category> query4 = session.createQuery("FROM Category where id = " + categoryId);
        Category category = query4.uniqueResult();
        return category;
    }
}
