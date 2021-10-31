package mahat.aviran.tvseriesfetcher.entities.persistence_mappers;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.persistence.PersistentGenre;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeriesFullEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TvSeriesMapper {

    private final GenreMapper genreMapper;
    private final TvSeasonMapper tvSeasonMapper;

    public PersistentTvSeries toPersistent(TvSeriesFullEntity tvSeriesFullEntity) {
        PersistentTvSeries persistentTvSeries = new PersistentTvSeries()
            .setId(tvSeriesFullEntity.getId())
            .setName(tvSeriesFullEntity.getName())
            .setFirstAirDate(tvSeriesFullEntity.getFirstAirDate())
            .setOriginalLanguage(tvSeriesFullEntity.getOriginalLanguage())
            .setOverview(tvSeriesFullEntity.getOverview())
            .setPosterPath(tvSeriesFullEntity.getPosterPath())
            .setPopularity(tvSeriesFullEntity.getPopularity())
            .setVoteAverage(tvSeriesFullEntity.getVoteAverage())
            .setVoteCount(tvSeriesFullEntity.getVoteCount())
            .setNumberOfEpisodes(tvSeriesFullEntity.getNumberOfEpisodes())
            .setNumberOfSeasons(tvSeriesFullEntity.getNumberOfSeasons())
            .setStatus(tvSeriesFullEntity.getStatus());

        Set<PersistentGenre> genres = tvSeriesFullEntity.getGenres().stream()
                .map(genreMapper::toPersistent)
                .collect(Collectors.toSet());
        persistentTvSeries.setGenres(genres);

        Set<PersistentTvSeason> seasons = tvSeriesFullEntity.getSeasons().stream()
                .map(tvSeason -> tvSeasonMapper.toPersistent(tvSeason, persistentTvSeries))
                .collect(Collectors.toSet());
        persistentTvSeries.setSeasons(seasons);

        return persistentTvSeries;
    }
}
