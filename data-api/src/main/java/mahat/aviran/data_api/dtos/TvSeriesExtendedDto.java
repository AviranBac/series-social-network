package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(callSuper = true) @AllArgsConstructor @NoArgsConstructor
public class TvSeriesExtendedDto extends TvSeriesDto {

    private List<TvSeasonDto> seasons = new ArrayList<>();

    public TvSeriesExtendedDto(TvSeriesDto tvSeriesDto) {
        super(tvSeriesDto);
    }

    public TvSeriesExtendedDto(TvSeriesExtendedDto tvSeriesExtendedDto) {
        super(tvSeriesExtendedDto);
        this.seasons = tvSeriesExtendedDto.seasons;
    }

    public static TvSeriesExtendedDto from(PersistentTvSeries persistentTvSeries, List<TvSeasonDto> tvSeasonDtos) {
        return new TvSeriesExtendedDto(from(persistentTvSeries))
                .setSeasons(tvSeasonDtos);
    }

    public TvSeriesExtendedDto sortSeasons() {
        this.seasons = this.seasons.stream()
                .map(TvSeasonDto::sortEpisodes)
                .sorted(Comparator.comparingInt(TvSeasonDto::getSeasonNumber)).collect(Collectors.toList());
        return this;
    }
}
