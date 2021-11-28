package mahat.aviran.apigateway.config.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.apigateway.utils.AuthorizationHeaderHelper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserResponseHeaderFilter implements GlobalFilter {

    private final AuthorizationHeaderHelper authorizationHeaderHelper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      return chain.filter(exchange)
              .then(Mono.fromRunnable(() ->
                authorizationHeaderHelper.extractUsername(exchange).ifPresent(username -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.getHeaders().add("username", username);
                    log.info("Added custom username header to response: " + username);
                })
            ));
    }
}
