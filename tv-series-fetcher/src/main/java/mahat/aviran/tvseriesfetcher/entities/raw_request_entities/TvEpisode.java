package mahat.aviran.tvseriesfetcher.entities.raw_request_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvEpisode {

    @JsonProperty(value = "air_date")
    private String airDate;
    @JsonProperty(value = "episode_number")
    private String episodeNumber;
    private String id;
    private String name;
    private String overview;
    @JsonProperty(value = "season_number")
    private String seasonNumber;
    @JsonProperty(value = "still_path")
    private String stillPath;
    @JsonProperty(value = "vote_average")
    private Double voteAverage;
    @JsonProperty(value = "vote_count")
    private Double voteCount;

    public String getStillPath() {
        return this.stillPath == null ?
                null :
                String.join("", "https://image.tmdb.org/t/p/w185", this.stillPath);
    }
}
