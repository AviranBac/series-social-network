package mahat.aviran.common.entities.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class UserDto {
    private String userName;
    private String firstName;
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDto> followers;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDto> following;
}
