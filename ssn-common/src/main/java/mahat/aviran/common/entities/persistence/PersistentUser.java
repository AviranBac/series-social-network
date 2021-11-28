package mahat.aviran.common.entities.persistence;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PersistentUser {

    @Id
    private String userName;
    
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(name = "watchlist_records",
            joinColumns = @JoinColumn(name = "user_name"),
            inverseJoinColumns = @JoinColumn(name = "episode_id"),
            foreignKey = @ForeignKey(name ="fk_user_name"),
            inverseForeignKey = @ForeignKey(name = "fk_episode_id"))
    private Set<PersistentTvEpisode> watchlistRecords = new HashSet<>();
}
