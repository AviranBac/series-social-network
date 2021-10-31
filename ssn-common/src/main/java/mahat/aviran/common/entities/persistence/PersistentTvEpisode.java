package mahat.aviran.common.entities.persistence;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "tv_episodes")
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode(exclude = "season") @AllArgsConstructor @NoArgsConstructor
public class PersistentTvEpisode {

    @Id
    private String id;
    private String name;
    private String episodeNumber;
    private String airDate;
    @Column(columnDefinition = "text")
    private String overview;
    private String stillPath;
    private double voteAverage;
    private int voteCount;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false, foreignKey = @ForeignKey(name = "fk_season_id"))
    private PersistentTvSeason season;
}
