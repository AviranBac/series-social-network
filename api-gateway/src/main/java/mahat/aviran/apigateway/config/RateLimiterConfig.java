package mahat.aviran.apigateway.config;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
            String actual = Strings.isEmpty(authorization) ? UUID.randomUUID().toString() : authorization;
            System.out.println(actual);
            return Mono.just(actual);
        };
    }
}
