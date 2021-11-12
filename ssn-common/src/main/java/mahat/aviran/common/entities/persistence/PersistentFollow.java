package mahat.aviran.common.entities.persistence;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "follows")
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PersistentFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "username_from", foreignKey = @ForeignKey(name = "fk_from"), nullable = false)
    private PersistentUser usernameFrom;

    @ManyToOne
    @JoinColumn(name = "username_to", foreignKey = @ForeignKey(name = "fk_to"), nullable = false)
    private PersistentUser usernameTo;
}
