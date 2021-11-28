package mahat.aviran.user_actions_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "mahat.aviran.user_actions_service", "mahat.aviran.common" })
@EntityScan(basePackages = { "mahat.aviran.user_actions_service.entities", "mahat.aviran.common.entities" })
@EnableJpaRepositories(basePackages = { "mahat.aviran.common.repositories" })
public class UserActionsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserActionsServiceApplication.class, args);
    }
}
