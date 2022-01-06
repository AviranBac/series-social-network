package mahat.aviran.common.entities.dtos.watchlist;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.dtos.TvSeasonDto;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;

import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(callSuper = true) @AllArgsConstructor @NoArgsConstructor
public class WatchlistTvSeasonDto extends TvSeasonDto {

    private WatchlistStatus watchlistStatus;

    public WatchlistTvSeasonDto(TvSeasonDto tvSeasonDto) {
        super(tvSeasonDto);
    }

    public static WatchlistTvSeasonDto from(PersistentTvSeason persistentTvSeason,
                                   List<TvEpisodeDto> tvEpisodeDtos,
                                   WatchlistStatus watchlistStatus) {
        return new WatchlistTvSeasonDto(from(persistentTvSeason, tvEpisodeDtos))
                .setWatchlistStatus(watchlistStatus);
    }
}
