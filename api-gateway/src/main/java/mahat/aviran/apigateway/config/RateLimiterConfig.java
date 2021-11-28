package mahat.aviran.apigateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Log4j2
public class RateLimiterConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
            String key = UUID.randomUUID().toString();

            if (!StringUtils.isBlank(authorization)) {
                try {
                    String payload = new String(Base64.getDecoder().decode(authorization.split("\\.")[1]));
                    key = objectMapper.readTree(payload.getBytes()).get("sub").asText();
                } catch (IOException e) {
                    log.error("Could not parse JWT token from authorization header: " + authorization);
                }
            }

            log.info("Current request's user key resolver for rate limiting: " + key);
            return Mono.just(key);
        };
    }
}
