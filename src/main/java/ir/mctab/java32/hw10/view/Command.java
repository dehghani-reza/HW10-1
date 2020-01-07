package ir.mctab.java32.hw10.view;

import ir.mctab.java32.hw10.entities.User;

public class Command {

    private User user;


    public void setUser(User user) {
        this.user = user;
    }

    public void commandList(User user) {

        if (user == null) {
            System.out.println("SignIn: 1 | signUp: 2 | showAllArticle: 3 | exit: 9");
        } else if (user.getRoles().stream().anyMatch(p -> p.getRoleName().equals("Admin")) && user.getRoles().stream().anyMatch(p -> p.getRoleName().equals("Writer"))) {
            System.out.println(Color.ANSI_BLUE + "showAllArticle: 3 "
                    + Color.ANSI_CYAN
                    + Color.ANSI_BLUE + "changing pass: 4 | see all of your article: 5 "
                    + Color.ANSI_CYAN
                    + "\ncostume article: 6 | new article: 7 | publish article: 8 | exit: 9 | signOut: 10"
                    + Color.ANSI_RESET
                    + "\nrepeal publish:11 | create category:12 | create tag:13 | delete Article:14 "
                    + Color.ANSI_RESET);
        } else if (user.getRoles().stream().anyMatch(p -> p.getRoleName().equals("Writer"))) {
            System.out.println(Color.ANSI_BLUE + "changing pass: 4 | see all of your article: 5"
                    + Color.ANSI_CYAN +
                    "\ncostume article: 6 | new article: 7 | exit: 9 | signOut: 10"
                    + Color.ANSI_RESET);
        } else if (user.getRoles().stream().anyMatch(p -> p.getRoleName().equals("Admin"))) {
            System.out.println(Color.ANSI_BLUE + "showAllArticle: 3 | changing pass: 4  | change role:15 | exit: 9 | signOut: 10"
                    + Color.ANSI_CYAN +
                    "\npublish article: 8 | repeal publish:11 | create category:12 | create tag:13 | delete Article:14"
                    + Color.ANSI_RESET);
        }
        if(user!=null && user.getRoles().stream().noneMatch(role -> role.getRoleName().equals("Writer"))){
            System.out.println(Color.ANSI_RED+"Change Role: 15"+Color.ANSI_RESET);
        }
    }

    public void commandCheck(int commandInput) throws RuntimeException {
        if (this.user == null && !(commandInput == 1 || commandInput == 2 || commandInput == 3 || commandInput == 9)) {
            throw new RuntimeException(Color.ANSI_RED + "False command first signIn or signUp" + Color.ANSI_RESET);
        }
        if (this.user != null && (commandInput == 1 || commandInput == 2)) {
            throw new RuntimeException(Color.ANSI_RED + "for this command you should exit first" + Color.ANSI_RESET);
        }
        if (commandInput > 15 || commandInput < 1) {
            throw new RuntimeException(Color.ANSI_RED + "wrong command" + Color.ANSI_RESET);
        }
        if (this.user != null && user.getRoles().stream().noneMatch(p -> p.getRoleName().equals("Writer")) && (commandInput == 5 || commandInput == 6 || commandInput == 7)) {
            throw new RuntimeException("you should have writer role");
        }
        if (this.user != null && (user.getRoles().stream().noneMatch(p -> p.getRoleName().equals("Admin"))) && (!(commandInput == 4 || commandInput == 5 || commandInput == 6 || commandInput == 7 || commandInput == 9 || commandInput == 10))) {
            throw new RuntimeException("Only admin can do this command");
        }
        if((user!=null && user.getRoles().stream().anyMatch(role -> role.getRoleName().equals("Writer")))&& commandInput==15){
            throw new RuntimeException(Color.ANSI_PURPLE+"Only Super admin can do this command"+Color.ANSI_RESET);
        }
    }
}
