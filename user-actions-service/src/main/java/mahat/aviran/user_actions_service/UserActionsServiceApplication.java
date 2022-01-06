package mahat.aviran.user_actions_service;

import mahat.aviran.common.helpers.WatchlistBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = { "mahat.aviran.user_actions_service.entities", "mahat.aviran.common.entities" })
@EnableJpaRepositories(basePackages = { "mahat.aviran.common.repositories" })
@Import(WatchlistBuilder.class)
public class UserActionsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserActionsServiceApplication.class, args);
    }
}
