package mahat.aviran.tvseriesfetcher.entities.persistance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tv_episodes")
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PersistentTvEpisode {

    @Id
    private String id;
    private String name;
    private String episodeNumber;
    private String airDate;
    @Column(columnDefinition = "text")
    private String overview;
    private String stillPath;
    @Column(precision = 1)
    private double voteAverage;
    private int voteCount;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(name = "fk_season_id"))
    private PersistentTvSeason season;
}
