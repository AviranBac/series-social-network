package mahat.aviran.user_actions_service.entities;


import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class FollowDetails {

    private FollowDetails.Action action;

    @NotBlank(message = "usernameFrom can not be blank")
    private String usernameFrom;

    @NotBlank(message = "usernameTo can not be blank")
    private String usernameTo;

    public enum Action {
        ADD,
        REMOVE;
    }
}
