package mahat.aviran.data_api.dtos.watchlist;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesExtendedDto;

import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(callSuper = true) @AllArgsConstructor @NoArgsConstructor
public class WatchlistTvSeriesDto extends TvSeriesExtendedDto {

    private WatchlistStatus watchlistStatus;

    public WatchlistTvSeriesDto(TvSeriesExtendedDto tvSeriesExtendedDto) {
        super(tvSeriesExtendedDto);
    }

    public static WatchlistTvSeriesDto from(PersistentTvSeries persistentTvSeries,
                                            List<TvSeasonDto> tvSeasonDtos,
                                            WatchlistStatus watchlistStatus) {
        return new WatchlistTvSeriesDto(from(persistentTvSeries, tvSeasonDtos))
                .setWatchlistStatus(watchlistStatus);
    }
}
