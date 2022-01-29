package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentGenre;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class GenreDto {

    private long id;
    private String name;

    public static GenreDto from(PersistentGenre persistentGenre) {
        return new GenreDto()
                .setId(persistentGenre.getId())
                .setName(persistentGenre.getName());
    }
}
