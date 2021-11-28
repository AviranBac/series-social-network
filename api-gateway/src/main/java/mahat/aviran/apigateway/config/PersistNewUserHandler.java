package mahat.aviran.apigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Log4j2
public class PersistNewUserHandler implements ServerAuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
        UserDto userDto = new UserDto(oidcUser.getPreferredUsername(),
                oidcUser.getGivenName(),
                oidcUser.getFamilyName(),
                Collections.emptyList(),
                Collections.emptyList());
        this.persistNewUserIfNeeded(userDto);

        return Mono.empty();
    }

    private void persistNewUserIfNeeded(UserDto userDto) {
        if (!userRepository.existsById(userDto.getUserName())) {
            PersistentUser persistentUser = new PersistentUser()
                    .setUserName(userDto.getUserName())
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName());
            persistentUser = userRepository.save(persistentUser);
            log.info("Saved new user to the database: " + persistentUser);
        }
    }
}
