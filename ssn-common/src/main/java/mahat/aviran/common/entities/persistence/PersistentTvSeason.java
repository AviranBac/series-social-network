package mahat.aviran.common.entities.persistence;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tv_seasons")
@Accessors(chain = true)
@Getter @Setter @ToString(exclude = "tvSeries") @EqualsAndHashCode(exclude = "tvSeries") @AllArgsConstructor @NoArgsConstructor
public class PersistentTvSeason {

    @Id
    private String id;
    private String name;
    private String seasonNumber;
    private String airDate;
    @Column(columnDefinition = "text")
    private String overview;
    private String posterPath;

    @ManyToOne
    @JoinColumn(name = "series_id", nullable = false, foreignKey = @ForeignKey(name = "fk_series_id"))
    private PersistentTvSeries tvSeries;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    private Set<PersistentTvEpisode> episodes;
}
