package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.persistence.PersistentGenre;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;

import java.util.Set;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvSeriesDto {

    protected String id;
    protected String name;
    protected String firstAirDate;
    protected String originalLanguage;
    protected String overview;
    protected String posterPath;
    protected double popularity;
    protected double voteAverage;
    protected int voteCount;
    protected int numberOfEpisodes;
    protected int numberOfSeasons;
    protected SeriesStatus status;
    protected Set<String> genres;

    public static TvSeriesDto from(PersistentTvSeries persistentTvSeries) {
        Set<String> genreNames = persistentTvSeries.getGenres().stream().map(PersistentGenre::getName).collect(Collectors.toSet());

        return new TvSeriesDto()
                .setId(persistentTvSeries.getId())
                .setName(persistentTvSeries.getName())
                .setFirstAirDate(persistentTvSeries.getFirstAirDate())
                .setOriginalLanguage(persistentTvSeries.getOriginalLanguage())
                .setOverview(persistentTvSeries.getOverview())
                .setPosterPath(persistentTvSeries.getPosterPath())
                .setPopularity(persistentTvSeries.getPopularity())
                .setVoteAverage(persistentTvSeries.getVoteAverage())
                .setVoteCount(persistentTvSeries.getVoteCount())
                .setNumberOfEpisodes(persistentTvSeries.getNumberOfEpisodes())
                .setNumberOfSeasons(persistentTvSeries.getNumberOfSeasons())
                .setStatus(persistentTvSeries.getStatus())
                .setGenres(genreNames);
    }
}
