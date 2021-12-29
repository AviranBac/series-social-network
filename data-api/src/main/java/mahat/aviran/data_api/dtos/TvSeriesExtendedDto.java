package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public TvSeriesExtendedDto sortSeasons() {
        this.seasons = this.seasons.stream()
                .map(TvSeasonDto::sortEpisodes)
                .sorted(Comparator.comparingInt(TvSeasonDto::getSeasonNumber)).collect(Collectors.toList());
        return this;
    }
}
