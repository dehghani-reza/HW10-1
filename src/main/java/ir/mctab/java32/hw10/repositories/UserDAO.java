package ir.mctab.java32.hw10.repositories;

import ir.mctab.java32.hw10.entities.Article;
import ir.mctab.java32.hw10.entities.Role;
import ir.mctab.java32.hw10.view.Color;
import ir.mctab.java32.hw10.entities.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.stream.Collectors;

public class UserDAO {
    Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    public User loginUser(String username, String password, User user) {
        Query<User> query = session.createQuery("FROM User where userName = '" + username + "' and password = '" + password + "'");
        user = query.uniqueResult();
        return user;
    }

    public User signup(String username, Long nationalId, String birthday) throws Exception {
        Query<User> query = session.createQuery("From User ");
        List<User> users = query.list();
        if (users.stream().anyMatch(p -> p.getUserName().equals(username))) {
            throw new Exception(Color.ANSI_RED + "this user name is reserved" + Color.ANSI_RESET);
        }
        User user = new User(username, nationalId, birthday);
        Query<Role> query1 = session.createQuery("From Role where  roleName = 'Writer' ");
        Role role = query1.uniqueResult();
        user.getRoles().add(role);
        session.save(user);
        return user;
    }

    public void changiPassword(User user, String pass) {
        user = session.load(User.class, user.getId());
        user.setPassword(pass);
        session.update(user);
    }

    public List userArticle(User user) throws Exception {
        Query<Article> query2 = session.createQuery("From Article where user_id = " + user.getId());
        List<Article> articles1 = query2.list();
        if (articles1.isEmpty()) {
            throw new Exception("you have no article");
        }
        return articles1;
    }

    public List<User> loadWriter() {
        Query<User> query = session.createQuery("From User");
        Query<Role> query1 = session.createQuery("From Role where  roleName = 'Writer' ");
        Role role = query1.uniqueResult();
        List<User> users = query.list().stream().filter(user -> user.getRoles().contains(role)).collect(Collectors.toList());
        return users;
    }

    public void addRole(Long id) {
        User user = session.load(User.class, id);
        if(user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("Admin"))){
            Query<Role> query1 = session.createQuery("From Role where  roleName = 'Admin' ");
            Role role = query1.uniqueResult();
            user.getRoles().remove(role);
            session.update(user);
            System.out.println("user with"+Color.ANSI_YELLOW+" ID: "+Color.ANSI_RESET+user.getId()+Color.ANSI_RED+" demoted"+Color.ANSI_RESET);
        } else {
            Query<Role> query1 = session.createQuery("From Role where  roleName = 'Admin' ");
            Role role = query1.uniqueResult();
            user.getRoles().add(role);
            session.update(user);
            System.out.println("user with"+Color.ANSI_YELLOW+" ID: "+Color.ANSI_RESET+user.getId()+Color.ANSI_GREEN+" promoted"+Color.ANSI_RESET);
        }
    }
}
