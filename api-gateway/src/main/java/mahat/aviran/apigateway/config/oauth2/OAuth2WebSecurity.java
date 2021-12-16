package mahat.aviran.apigateway.config.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.DelegatingServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;

@EnableWebFluxSecurity
@RequiredArgsConstructor
public class OAuth2WebSecurity {

    private final PersistNewUserHandler persistNewUserHandler;
    private final RedirectServerAuthenticationSuccessHandler redirectServerAuthenticationSuccessHandler;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange()
                .authenticated()
                .and().oauth2Login()
                .authenticationSuccessHandler(authenticationSuccessHandler())
                .and().logout()
                .and().oauth2ResourceServer().jwt();
        return http.build();
    }

    private ServerAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new DelegatingServerAuthenticationSuccessHandler(persistNewUserHandler, redirectServerAuthenticationSuccessHandler);
    }
}
