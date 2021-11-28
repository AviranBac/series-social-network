package mahat.aviran.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@EntityScan(basePackages = { "mahat.aviran.common.entities" })
@EnableJpaRepositories(basePackages = { "mahat.aviran.common.repositories" })
@Import(RedirectServerAuthenticationSuccessHandler.class)
public class ApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@GetMapping()
	@ResponseBody
	public String clientPlaceholder(@AuthenticationPrincipal OidcUser oidcUser,
									@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client) {
		System.out.println(oidcUser);
		System.out.println(client.getAccessToken().getTokenValue());
		return "Placeholder for client";
	}
}
