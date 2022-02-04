package mahat.aviran.common.entities.dtos.watchlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.dtos.TvSeriesDto;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(callSuper = true) @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WatchlistTvSeriesDto extends TvSeriesDto {

    private List<WatchlistTvSeasonDto> seasons = new ArrayList<>();
    private WatchlistStatus watchlistStatus;

    public WatchlistTvSeriesDto(TvSeriesDto tvSeriesDto) {
        super(tvSeriesDto);
    }

    public static WatchlistTvSeriesDto from(PersistentTvSeries persistentTvSeries,
                                            List<WatchlistTvSeasonDto> watchlistTvSeasonDtos,
                                            WatchlistStatus watchlistStatus) {
        return new WatchlistTvSeriesDto(from(persistentTvSeries))
                .setSeasons(watchlistTvSeasonDtos)
                .setWatchlistStatus(watchlistStatus);
    }
}
