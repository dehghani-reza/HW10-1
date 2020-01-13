package ir.mctab.java32.hw10.view;


import ir.mctab.java32.hw10.config.log4j.Log4j;
import ir.mctab.java32.hw10.entities.*;
import ir.mctab.java32.hw10.repositories.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;
import java.util.stream.Collectors;

public class Remote {
    Logger logger = Log4j.getLoggerRemote();

    Session session;
    UserDAO userDAO = new UserDAO();
    ArticleDAO articleDAO = new ArticleDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    TagDAO tagDAO = new TagDAO();
    AddressDAO addressDAO = new AddressDAO();

    //*********************************************
    public Remote(Session session) {
        this.session = session;
        userDAO.setSession(session);
        articleDAO.setSession(session);
        categoryDAO.setSession(session);
        tagDAO.setSession(session);
    }

    //*********************************************

    //1
    public User loginCommand(Scanner scanner, User user) {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();
        user = userDAO.loginUser(username, password, user);
        logger.info("with username: " + username + " and password: " + password + " try to login at: " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        return user;
    }

    //2
    public User signupCommand(Scanner scannerInt, Scanner scanner) throws Exception {
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();
        System.out.println("Enter your birthday: YYYY-MM-DD");
        String birthday = scanner.nextLine();
        if (birthday.length() != 10) {
            throw new Exception(Color.ANSI_RED + "Bad birthday date please write in correct order" + Color.ANSI_RESET);
        }
        System.out.println("Enter your nationalCode: ");
        Long nationalId = scannerInt.nextLong();
        if (nationalId < 10000000 || nationalId > 99999999) {
            throw new Exception(Color.ANSI_RED + "Bad NationalCode please write correct" + Color.ANSI_RESET);
        }
        logger.info("with username: " + username + " birthday: " + birthday + " some body try to sign up");
        User user = userDAO.signup(username, nationalId, birthday);
        System.out.println("user " + user.getUserName() + " with id " + user.getId() + " has been created"
                + "\nremember your default pass is your nationalNumber");

        //*******Address******
        System.out.println("please enter your zip code: ");
        Long zipCode = scannerInt.nextLong();
        System.out.println("please enter your city name: ");
        String city = scanner.nextLine();
        System.out.println("please enter your street name: ");
        String street = scanner.nextLine();
        System.out.println("please enter your alley name: ");
        String alley = scanner.nextLine();
        addressDAO.createAddress(zipCode,city,street,alley,user);

        return user;
    }

    //3
    public void showAllArticle(Scanner scannerInt) throws Exception {
        List<Article> articles = articleDAO.showAllArticle();
        articles.stream().forEach(article -> System.out.println(Color.ANSI_YELLOW + "Id: " + Color.ANSI_RESET + article.getId()
                + Color.ANSI_YELLOW + "\tTitle: " + Color.ANSI_RESET + article.getTitle()
                + Color.ANSI_YELLOW + "\t\tBrief: " + Color.ANSI_RESET + article.getBrief()));
        System.out.println("Please enter id of your article which you want to see: \nif you don't press '0' ");
        Long id = null;
        if (scannerInt.hasNextLong()) {
            id = scannerInt.nextLong();
        } else {
            throw new Exception("invalid input");
        }
        Long finalId = id;
        Optional<Article> article = articles.stream().filter(p -> p.getId().equals(finalId)).findFirst();
        if (article.isPresent()) {
            System.out.println(article);
        }
    }

    //4
    public void changePasswprd(Scanner scanner, User user) {
        System.out.println("enter your new password: ");
        String pass = scanner.nextLine();
        user = session.load(User.class, user.getId());
        user.setPassword(pass);
        userDAO.changePassword(user, pass);
        System.out.println(Color.ANSI_GREEN + "your password changed" + Color.ANSI_RESET);
        logger.info(user.toString() + "changed password");
    }

    //5
    public void userArticle(User user) throws Exception {
        List<Article> articles1 = userDAO.userArticle(user);
        articles1.forEach(System.out::println);

    }

    //6
    public Article cotumeArticle(Scanner scanner, Scanner scannerInt, User user) throws Exception {
        userArticle(user);
        System.out.println("Insert your article id: ");
        Long articleId = scannerInt.nextLong();
        Article article = articleDAO.loadArticle(articleId);
        if (!article.getUser().getId().equals(user.getId())) {
            throw new Exception("its not your article");
        }
        System.out.println("Insert your new data in this order: [brief,content,title]");
        String costume = scanner.nextLine();
        String[] costumeArray = costume.split(",");
        if (costumeArray.length != 3) {
            throw new Exception("wrong input");
        }
        article = articleDAO.costumeArticle(costumeArray, article);
        if (article != null) {
            System.out.println("Article with id: " + article.getId() + " has been changed");
        }
        return article;
    }

    //7
    public void nawArticle(Scanner scanner, Scanner scannerInt, User user) {
        System.out.println("please enter brief of your article: ");
        String brief = scanner.nextLine();
        System.out.println("please enter content of your article: ");
        String content = scanner.nextLine();
        boolean isPublish = false;
        System.out.println("please enter title of your article: ");
        String title = scanner.nextLine();
        System.out.println("here is list of category: ");
        List<Category> categories = categoryDAO.categoryList();
        categories.forEach(System.out::println);
        System.out.println("Enter category id: ");
        Long categoryId = scannerInt.nextLong();
        Category category = categoryDAO.loadCategory(categoryId);
        //*******tag***********
        List<Tag> tagList = tagDAO.loadAllTags();
        Set<Tag> articleTags = new HashSet<>();
        int tagCommand = -1;
        while(tagCommand!=0) {
            if(articleTags.size()>5 || articleTags.size()==tagList.size())break;
            tagList.stream().filter(tag-> !articleTags.contains(tag)).forEach(System.out::println);
            System.out.println("if you want to add more tag enter 1 and if you dont enter 0 ");
            tagCommand = scannerInt.nextInt();
            if(tagCommand!=1 && tagCommand!=0){
                System.out.println(Color.ANSI_RED+"Wrong command"+Color.ANSI_RESET);
                continue;
            }
            System.out.println("please enter  your tag id which you want to add: ");
            articleTags.add(tagDAO.findById(scannerInt.nextLong()));
        }
        Article article = articleDAO.saveArticle(title, brief, content, isPublish, user, category , articleTags);
        System.out.println("Article with " + Color.ANSI_YELLOW + "id: " + Color.ANSI_RESET + article.getId() + " has been created");
        logger.info(article.toString() + "has been created at: " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
    }

    //8
    public void publishArticle(Scanner scannerInt, User user) throws Exception {
        List<Article> articles2 = articleDAO.findByPredicate(Article::isPublish);
        articles2.forEach(System.out::println);
        System.out.println("choose article which you want to publish ");
        Long publishId = scannerInt.nextLong();
        articleDAO.publishArticle(user, publishId);
        System.out.println(Color.ANSI_GREEN + "article published" + Color.ANSI_RESET);
    }

    //11
    public void repealPublish(Scanner scannerInt) {
        List<Article> articles = articleDAO.showAllArticle();
        List<Article> articleList = articles.stream().filter(Article::isPublish).collect(Collectors.toList());
        articleList.forEach(System.out::println);
        System.out.println("please enter id of your article which you want to repeal the publish status: ");
        Long id = scannerInt.nextLong();
        Article article = articleDAO.repealArticle(id);
        System.out.println(article);
    }

    //12
    public Category createCategory(Scanner scanner) throws Exception {
        System.out.println("Enter Title of this category");
        String title1 = scanner.nextLine();
        System.out.println("Enter description of your category");
        String description = scanner.nextLine();
        Category category = categoryDAO.addCategory(title1, description);
        return category;
    }

    //13
    public Tag createTag(Scanner scanner) throws Exception {
        System.out.println("please enter tag name: ");
        String name = scanner.nextLine();
        Tag tag = tagDAO.createTeg(name);
        System.out.println(Color.ANSI_GREEN+"tag with name: "+name+" created"+Color.ANSI_RESET);
        return tag;
    }

    //14
    public void deleteArticle(Scanner scannerInt) throws Exception {
        showAllArticle(scannerInt);
        System.out.println("please enter id of article which you want to delete: ");
        Long id = scannerInt.nextLong();
        articleDAO.deleteArticle(id);
        System.out.println("article with id: " + Color.ANSI_YELLOW + id + Color.ANSI_RESET + " has been deleted");
    }

    //15
    public void changeRole(Scanner scannerInt) {
        List<User> users = userDAO.loadWriter();
        users.forEach(user -> System.out.println(Color.ANSI_YELLOW + "ID: " + Color.ANSI_RESET + user.getId()
                + Color.ANSI_YELLOW + " username: " + Color.ANSI_RESET + user.getUserName()
                +Color.ANSI_YELLOW+" role: " +Color.ANSI_RESET+user.getRoles().stream().map(Role::getRoleName).collect(Collectors.joining())));
        System.out.println("which user you want to promote or demote enter id of that user: ");
        Long id = scannerInt.nextLong();
        userDAO.addRole(id);
    }

    //dashboard
    public void dashboard(User user) throws Exception {
       List<Long> articles =  articleDAO.findByFunction((Article::getId) ,user);
        System.out.println("your past 10 article: ");
        if(articles.isEmpty()){
            throw new Exception("you have no article");
        }
       articles.stream().limit(10).forEach(aLong -> System.out.println(aLong+" ,"));
    }

}
