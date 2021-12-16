package mahat.aviran.tvseriesfetcher.entities.raw_request_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSeason {

    @JsonProperty(value = "air_date")
    private String airDate;
    private List<TvEpisode> episodes;
    private String name;
    private String overview;
    private String id;
    @JsonProperty(value = "poster_path")
    private String posterPath;
    @JsonProperty(value = "season_number")
    private String seasonNumber;

    public String getPosterPath() {
        return this.posterPath == null ?
                null :
                String.join("", "https://image.tmdb.org/t/p/w185", this.posterPath);
    }
}
