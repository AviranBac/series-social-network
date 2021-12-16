package mahat.aviran.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@Controller
@EntityScan(basePackages = { "mahat.aviran.common.entities" })
@EnableJpaRepositories(basePackages = { "mahat.aviran.common.repositories" })
@Import(RedirectServerAuthenticationSuccessHandler.class)
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
