package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Accessors(chain = true)
@Getter @Setter @ToString(callSuper = true) @EqualsAndHashCode(callSuper = true) @AllArgsConstructor @NoArgsConstructor
public class TvSeriesExtendedDto extends TvSeriesDto {

    private List<TvSeasonDto> seasons = new ArrayList<>();

    public TvSeriesExtendedDto(TvSeriesDto tvSeriesDto) {
        this.id = tvSeriesDto.id;
        this.name = tvSeriesDto.name;
        this.firstAirDate = tvSeriesDto.firstAirDate;
        this.originalLanguage = tvSeriesDto.originalLanguage;
        this.overview = tvSeriesDto.overview;
        this.posterPath = tvSeriesDto.posterPath;
        this.popularity = tvSeriesDto.popularity;
        this.voteAverage = tvSeriesDto.voteAverage;
        this.voteCount = tvSeriesDto.voteCount;
        this.numberOfEpisodes = tvSeriesDto.numberOfEpisodes;
        this.numberOfSeasons = tvSeriesDto.numberOfSeasons;
        this.status = tvSeriesDto.status;
        this.genres = tvSeriesDto.genres;
    }
}
