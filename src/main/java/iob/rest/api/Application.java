package iob.rest.api;

import iob.rest.api.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

//    @Bean
//    public JwtUtil jwtUtil() {
//        return new JwtUtil();
//    }
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
