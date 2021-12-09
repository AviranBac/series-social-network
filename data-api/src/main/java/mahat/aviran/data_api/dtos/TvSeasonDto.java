package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;

import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvSeasonDto {

    private String id;
    private String name;
    private String seasonNumber;
    private String airDate;
    private String overview;
    private String posterPath;
    private List<TvEpisodeDto> episodes;
}
