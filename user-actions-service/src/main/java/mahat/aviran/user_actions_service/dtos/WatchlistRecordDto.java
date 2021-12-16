package mahat.aviran.user_actions_service.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;

import java.util.Set;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class WatchlistRecordDto {

    private String username;
    private Set<TvEpisodeDto> tvEpisodes;
}
