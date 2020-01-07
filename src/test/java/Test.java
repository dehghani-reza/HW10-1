import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0 ; i<100 ; i++) {
            System.out.println(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
            Thread.sleep(2000);
        }
    }
}
