package kr.hs.ide.nextto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NextToApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextToApplication.class, args);
    }

}
