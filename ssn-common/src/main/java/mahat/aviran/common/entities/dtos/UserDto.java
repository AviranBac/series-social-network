package mahat.aviran.common.entities.dtos;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class UserDto {

    private String userName;
    private String firstName;
    private String lastName;
}
