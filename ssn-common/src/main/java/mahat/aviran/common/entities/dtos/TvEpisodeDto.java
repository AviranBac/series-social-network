package mahat.aviran.common.entities.dtos;

import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvEpisodeDto {

    private String id;
    private String name;
    private String episodeNumber;
    private String seasonNumber;
    private String airDate;
    private String overview;
    private String stillPath;
    private double voteAverage;
    private int voteCount;
}
