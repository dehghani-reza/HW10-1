package ir.mctab.java32.hw10.repositories;


import ir.mctab.java32.hw10.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw10.entities.Article;
import ir.mctab.java32.hw10.entities.Category;
import ir.mctab.java32.hw10.entities.Role;
import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.hibernate.query.Query;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.Collections;


import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleDAOTest {

    ArticleDAO articleDAO = new ArticleDAO();
    Session session = Mockito.mock(Session.class);


    @Test
    void showAllArticle() {
        Session session = HibernateUtil.getSession().openSession();
        articleDAO.setSession(session);
        assertEquals(1, articleDAO.showAllArticle().size());
    }

    @Test
    void loadArticle() {
        Session session = HibernateUtil.getSession().openSession();
        articleDAO.setSession(session);
        Long id = 1L;
        assertEquals(1L, articleDAO.loadArticle(id).getId());
    }

    // when the real test is start

    @Test
    void publishArticle() throws Exception {
        Long publishId = 1L;
        Session session = Mockito.mock(Session.class);
        articleDAO.setSession(session);
        User user = new User();
        user.setRoles(Collections.singleton(new Role("Admin")));
        Article article = new Article();
        article.setPublish(false);
        Mockito.when(session.load(Article.class, publishId)).thenReturn(article);
        articleDAO.publishArticle(user, publishId);
        assertEquals(true, article.isPublish());
    }

    @Test
    void repealArticle() {
        articleDAO.setSession(session);
        Long id = 1L;
        Article article = new Article();
        article.setPublish(true);
        Mockito.when(session.load(Article.class, id)).thenReturn(article);
        articleDAO.repealArticle(id);
        assertEquals(false, article.isPublish());

    }

    @Test
    void findByPredicate() {
        articleDAO.setSession(session);
        Query<Article> articleQuery = Mockito.mock(Query.class);
        Predicate<Article> published = Article::isPublish;
        Article article1 = new Article();
        article1.setPublish(false);
        Article article2 = new Article();
        article1.setPublish(true);
        List<Article> articles = new ArrayList<>(){{add(article1);add(article2);}};
        Mockito.when(session.createQuery("From Article")).thenReturn(articleQuery);
        Mockito.when(articleQuery.list()).thenReturn(articles);
        List<Article> articleList = articleDAO.findByPredicate(published);
        assertEquals(1,articleList.size());

    }

    @Test
    void findByFunction() {
        articleDAO.setSession(session);
        Query<Article> articleQuery = Mockito.mock(Query.class);
        User user = new User();
        user.setId(1L);
        Article article1 = new Article();
        article1.setUser(user);
        article1.setId(1L);
        Article article2 = new Article();
        article2.setUser(user);
        article2.setId(2L);
        List<Article> articles = new ArrayList<>(){{add(article1);add(article2);}};
        Function<Article,Long> function = Article::getId;
        Mockito.when(session.createQuery("From Article where  user_id =" +user.getId())).thenReturn(articleQuery);
        Mockito.when(articleQuery.list()).thenReturn(articles);
        List<Long> articleList = articleDAO.findByFunction(function,user);
        assertEquals(Optional.of(3L),articleList.stream().reduce(Long::sum));
    }
}