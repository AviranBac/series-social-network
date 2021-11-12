package mahat.aviran.common.entities.dtos;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class FollowDto {

    private String from;
    private String to;
}
