package ir.mctab.java32.hw10;

import ir.mctab.java32.hw10.config.hibernate.HibernateUtil;
import ir.mctab.java32.hw10.config.log4j.Log4j;
import ir.mctab.java32.hw10.entities.*;
import ir.mctab.java32.hw10.view.Color;
import ir.mctab.java32.hw10.view.Command;
import ir.mctab.java32.hw10.view.Remote;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArticleApp {
    public static void main(String[] args) {
        //scanner for int and string
        Scanner scanner = new Scanner(System.in);
        Scanner scannerInt = new Scanner(System.in);

        //hibernate session
        SessionFactory sessionFactory = HibernateUtil.getSession();
        Session session = sessionFactory.openSession();

        //git address
        System.out.println("git address: https://github.com/dehghani-reza/HW10-1.git");

        //logging
        Logger logger = Log4j.getLogger();

        //all that need for app in classes
        User user = null;
        Command command = new Command();
        int inputCommand = 0;
        Remote remote = new Remote(session);

logger.info("application going to start at: "+DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        do {
            session.beginTransaction();
            command.setUser(user);
            command.commandList(user);
            try {
                inputCommand = scannerInt.nextInt();
                command.commandCheck(inputCommand);
            } catch (InputMismatchException e) {
                System.out.println(Color.ANSI_RED + "wrong type of command" + Color.ANSI_RESET);
                scannerInt = new Scanner(System.in);
                session.getTransaction().rollback();
                continue;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                session.getTransaction().rollback();
                continue;
            }


            switch (inputCommand) {
                case 1:
                    user = remote.loginCommand(scanner, user);
                    try {
                        remote.dashboard(user);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (user == null) {
                        System.out.println(Color.ANSI_PURPLE + "Wrong Username or Password" + Color.ANSI_GREEN + "\ntry again...." + Color.ANSI_RESET);
                        logger.error("failed to login");
                        session.getTransaction().rollback();
                        continue;
                    }
                    System.out.println(Color.ANSI_CYAN + "Hello " + user.getUserName() + Color.ANSI_RESET);
                    break;
                case 2:
                    try {
                        user = remote.signupCommand(scannerInt, scanner);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 3:
                    try {
                        remote.showAllArticle(scannerInt);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 4:
                    remote.changePasswprd(scanner, user);
                    break;
                case 5:
                    try {
                        remote.userArticle(user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().commit();
                        continue;
                    }
                    break;
                case 6:
                    try {
                        remote.cotumeArticle(scanner, scannerInt, user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 7:
                    try {
                        remote.nawArticle(scanner, scannerInt, user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 8:
                    try {
                        remote.publishArticle(scannerInt, user);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 10:
                    logger.info(user.toString()+" sign out at: "+DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
                    user = null;
                    break;
                case 11:
                    try {
                        remote.repealPublish(scannerInt);
                    }catch (Exception e){
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 12:
                    try {
                        remote.createCategory(scanner);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 13:
                    try {
                        remote.createTag(scanner);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 14:
                    try {
                        remote.deleteArticle(scannerInt);
                    } catch (Exception e) {
                        System.out.println(Color.ANSI_RED + e.getMessage() + Color.ANSI_RESET);
                        logger.error(user.toString()+" get this error: "+e.getMessage());
                        session.getTransaction().rollback();
                        continue;
                    }
                    break;
                case 15:
                    remote.changeRole(scannerInt);
                    break;

            }
            session.getTransaction().commit();
        } while (inputCommand != 9);
        logger.info("app ended at : "+DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
        System.out.println("have a nice day .....");


    }//end of method main

}
