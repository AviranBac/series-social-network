package mahat.aviran.common.entities.persistence;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.SeriesStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tv_series")
@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PersistentTvSeries {

    @Id
    private String id;
    private String name;
    private String firstAirDate;
    private String originalLanguage;
    @Column(columnDefinition = "text")
    private String overview;
    private String posterPath;
    private double popularity;
    private double voteAverage;
    private int voteCount;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private SeriesStatus status;

    @ManyToMany
    @JoinTable(name = "series_to_genre",
               joinColumns = @JoinColumn(name = "series_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"),
               foreignKey = @ForeignKey(name ="fk_series_id"),
               inverseForeignKey = @ForeignKey(name = "fk_genre_id"))
    private Set<PersistentGenre> genres = new HashSet<>();

    @OneToMany(mappedBy = "tvSeries", cascade = CascadeType.ALL)
    private Set<PersistentTvSeason> seasons = new HashSet<>();
}
