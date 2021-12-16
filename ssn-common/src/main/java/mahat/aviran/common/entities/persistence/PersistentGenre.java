package mahat.aviran.common.entities.persistence;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genres")
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PersistentGenre {

    @Id
    private long id;
    private String name;
}
