package mahat.aviran.common.entities.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentUser;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class UserDto {

    private String userName;
    private String firstName;
    private String lastName;

    public static UserDto from(PersistentUser persistentUser) {
        return new UserDto()
                .setUserName(persistentUser.getUserName())
                .setFirstName(persistentUser.getFirstName())
                .setLastName(persistentUser.getLastName());
    }
}
