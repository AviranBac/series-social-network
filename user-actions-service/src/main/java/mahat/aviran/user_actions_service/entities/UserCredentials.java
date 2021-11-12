package mahat.aviran.user_actions_service.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class UserCredentials {

    @NotBlank(message = "userName can not be blank")
    private String userName;

    @NotBlank(message = "password can not be blank")
    private String password;
}
