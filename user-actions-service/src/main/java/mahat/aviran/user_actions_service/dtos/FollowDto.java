package mahat.aviran.user_actions_service.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentFollow;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class FollowDto {

    private String from;
    private String to;

    public static FollowDto from(PersistentFollow persistentFollow) {
        return new FollowDto()
                .setFrom(persistentFollow.getUsernameFrom().getUserName())
                .setTo(persistentFollow.getUsernameTo().getUserName());
    }
}
