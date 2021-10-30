package mahat.aviran.tvseriesfetcher.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSeries {

    @JsonProperty(value = "first_air_date")
    private String firstAirDate;
    private String id;
    private String name;
    private List<Genre> genres;
    @JsonProperty(value = "original_language")
    private String originalLanguage;
    private String overview;
    @JsonProperty(value = "poster_path")
    private String posterPath;
    private Double popularity;
    @JsonProperty(value = "vote_average")
    private Double voteAverage;
    @JsonProperty(value = "vote_count")
    private int voteCount;
    @JsonProperty(value = "number_of_episodes")
    private int numberOfEpisodes;
    @JsonProperty(value = "number_of_seasons")
    private int numberOfSeasons;
    private SeriesStatus status;

    public String getPosterPath() {
        return String.join("", "https://image.tmdb.org/t/p/w185", this.posterPath);
    }
}
