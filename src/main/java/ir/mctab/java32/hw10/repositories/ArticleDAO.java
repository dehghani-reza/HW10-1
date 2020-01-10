package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Article;
import ir.mctab.java32.hw10.entities.Category;
import ir.mctab.java32.hw10.entities.Tag;
import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArticleDAO implements FindAll<Article,Long> {

    Session session;


    public void setSession(Session session) {
        this.session = session;
    }

    public List<Article> showAllArticle() {
        Query<Article> query1 = session.createQuery("From Article");
        List<Article> articles = query1.list();
        return articles;
    }


    public Article loadArticle(Long id) {
        Article article = session.load(Article.class, id);
        System.out.println(article);
        return article;
    }

    public Article costumeArticle(String[] costumeArray, Article article) {
        article.setBrief(costumeArray[0]);
        article.setContent(costumeArray[1]);
        article.setTitle(costumeArray[2]);
        article.setLastUpdateDate(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        session.update(article);
        return article;
    }

    public Article saveArticle(String title, String brief, String content, boolean isPublish, User user, Category category , Set<Tag> tagSet) {
        String createDate = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        Article article = new Article(title, brief, content, createDate, isPublish, user, category);
        article.setTagSet(tagSet);
        session.save(article);
        return article;
    }

    public void publishArticle(User user, Long publishId) throws Exception {
        Article article1 = session.load(Article.class, publishId);
        if (user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("Admin"))) {
            throw new Exception("you cant publish article");
        }
        if (user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("Writer")) && !article1.getUser().getId().equals(user.getId())) {
            throw new Exception("you cant publish this article you can only publish article that you owned");
        }
        article1.setPublish(true);
        article1.setPublishDate(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        session.update(article1);
    }

    public List<Article> loadPublishArticle() {
        Query<Article> query5 = session.createQuery("From Article where isPublish = false ");
        List<Article> articles2 = query5.list();
        return articles2;
    }

    public Article repealArticle(Long id) {
        Article article = session.load(Article.class, id);
        article.setPublish(false);
        article.setPublishDate(null);
        session.update(article);
        return article;
    }

    public void deleteArticle(Long id) {
        Article article = session.load(Article.class, id);
        session.delete(article);
    }
//****************************Generic Method********************************
    @Override
    public List<Article> findByPredicate(Predicate<Article> predicate) {
        Query<Article> query1 = session.createQuery("From Article");
        List<Article> articles = query1.list();
        articles = articles.stream().filter(predicate).collect(Collectors.toList());
        return articles;
    }

    @Override
    public List<Long> findByFunction(Function<Article, Long> function , User user) {
        Query<Article> query1 = session.createQuery("From Article where  user_id =" +user.getId());
        List<Article> articles = query1.list();
        List<Long> articles1 = articles.stream().map(function).collect(Collectors.toList());
        return articles1;
    }
}
