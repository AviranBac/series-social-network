package mahat.aviran.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.apigateway.utils.AuthorizationHeaderHelper;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RateLimiterConfig {

    private final AuthorizationHeaderHelper authorizationHeaderHelper;

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String key = authorizationHeaderHelper.extractUsername(exchange)
                    .orElse(UUID.randomUUID().toString());
            log.info("Current request's user key resolver for rate limiting: " + key);
            return Mono.just(key);
        };
    }
}
