/*
 * ClassName: MainController.java
 * Author: Alexandru-Marian Atanasiu, alexandru.atanasiu@outlook.com
 * Created: 20-10-2016
 * Last Updated: 20-10-2016
 * Version: 0.1
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EntryPoint {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(EntryPoint.class, args);
    }
}