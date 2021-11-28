package mahat.aviran.apigateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuthorizationHeaderHelper {

    private final ObjectMapper objectMapper;

    public Optional<String> extractUsername(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst("Authorization");
        Optional<String> username = Optional.empty();

        if (!StringUtils.isBlank(authorization)) {
            try {
                String payload = new String(Base64.getDecoder().decode(authorization.split("\\.")[1]));
                username = Optional.ofNullable(objectMapper.readTree(payload.getBytes()).get("sub").asText());
            } catch (IOException e) {
                log.error("Could not parse JWT token from authorization header: " + authorization);
            }
        }

        return username;
    }
}
