package mahat.aviran.tvseriesfetcher.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSeries {

    @JsonProperty(value = "first_air_date")
    private String firstAirDate;
    private String id;
    private String name;
    @JsonProperty(value = "original_language")
    private String originalLanguage;
    private String overview;
    @JsonProperty(value = "poster_path")
    private String posterPath;
    private Double popularity;
    @JsonProperty(value = "vote_average")
    private Double voteAverage;

    public String getPosterPath() {
        return String.join("", "https://image.tmdb.org/t/p/w185", this.posterPath);
    }
}
