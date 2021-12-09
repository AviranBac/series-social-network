package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.SeriesStatus;

import java.util.List;
import java.util.Set;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvSeriesExtendedDto {

    private String id;
    private String name;
    private String firstAirDate;
    private String originalLanguage;
    private String overview;
    private String posterPath;
    private double popularity;
    private double voteAverage;
    private int voteCount;
    private int numberOfEpisodes;
    private int numberOfSeasons;
    private SeriesStatus status;
    private Set<String> genres;
    private List<TvSeasonDto> seasons;
}
