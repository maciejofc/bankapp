package pl.maciejowsky.bankapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.maciejowsky.bankapp.model.User;
import pl.maciejowsky.bankapp.service.UserService;

@SpringBootApplication
public class BankappApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(BankappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        for (int i = 0; i < 10; i++) {
//            User user = new User();
//            user.setEmail("email" + i);
//            user.setPassword("password" + i);
//            userService.registerUser(user);
//        }


    }
}
