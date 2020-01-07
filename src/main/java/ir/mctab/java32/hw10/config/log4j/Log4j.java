package ir.mctab.java32.hw10.config.log4j;

import ir.mctab.java32.hw10.ArticleApp;
import ir.mctab.java32.hw10.view.Remote;
import org.apache.log4j.Logger;

public class Log4j{

    final static Logger logger = Logger.getLogger(ArticleApp.class);
    final static Logger loggerRemote = Logger.getLogger(Remote.class);

    private Log4j() {

    }

    public static Logger getLogger() {
        return logger;
    }

    public static Logger getLoggerRemote() {
        return loggerRemote;
    }
}
