package mahat.aviran.common.helpers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.dtos.TvSeasonDto;
import mahat.aviran.common.entities.dtos.TvSeriesDto;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistStatus;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistTvSeasonDto;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistTvSeriesDto;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@Getter @RequiredArgsConstructor
@Log4j2
public class WatchlistBuilder {

    private final TvEpisodeRepository tvEpisodeRepository;

    public Set<PersistentTvSeries> getUserWatchlistSeries(String username) {
        return this.tvEpisodeRepository.getWatchlistEpisodes(username)
                .stream()
                .collect(Collectors.groupingBy(PersistentTvEpisode::getSeason, Collectors.toList()))
                .keySet()
                .stream()
                .collect(Collectors.groupingBy(PersistentTvSeason::getTvSeries, Collectors.toList()))
                .keySet();
    }

    @Transactional
    public List<WatchlistTvSeriesDto> getUserWatchlist(String username) {
        Map<PersistentTvSeason, List<PersistentTvEpisode>> watchlistSeasonsAndEpisodes = this.tvEpisodeRepository.getWatchlistEpisodes(username)
                .stream()
                .collect(Collectors.groupingBy(PersistentTvEpisode::getSeason, Collectors.toList()));
        Map<PersistentTvSeries, List<PersistentTvSeason>> watchlistSeriesAndSeasons = watchlistSeasonsAndEpisodes.keySet()
                .stream()
                .collect(Collectors.groupingBy(PersistentTvSeason::getTvSeries, Collectors.toList()));

        Set<String> watchlistSeriesIds = watchlistSeriesAndSeasons.keySet()
                .stream()
                .map(PersistentTvSeries::getId)
                .collect(Collectors.toSet());
        Map<PersistentTvSeason, List<PersistentTvEpisode>> totalSeasonsAndEpisodes = this.tvEpisodeRepository.getEpisodeIdsBySeriesIds(watchlistSeriesIds)
                .stream()
                .collect(Collectors.groupingBy(PersistentTvEpisode::getSeason, Collectors.toList()));
        Map<PersistentTvSeries, List<PersistentTvSeason>> totalSeriesAndSeasons = totalSeasonsAndEpisodes.keySet()
                .stream()
                .collect(Collectors.groupingBy(PersistentTvSeason::getTvSeries, Collectors.toList()));

        return watchlistSeriesAndSeasons.keySet()
                .stream()
                .map(persistentTvSeries -> this.convertToWatchlistDto(
                        persistentTvSeries,
                        watchlistSeriesAndSeasons,
                        totalSeriesAndSeasons,
                        watchlistSeasonsAndEpisodes,
                        totalSeasonsAndEpisodes
                ))
                .sorted(Comparator.comparing(TvSeriesDto::getName))
                .collect(Collectors.toList());
    }

    private WatchlistTvSeriesDto convertToWatchlistDto(PersistentTvSeries persistentTvSeries,
                                                       Map<PersistentTvSeries, List<PersistentTvSeason>> watchlistSeriesAndSeasons,
                                                       Map<PersistentTvSeries, List<PersistentTvSeason>> totalSeriesAndSeasons,
                                                       Map<PersistentTvSeason, List<PersistentTvEpisode>> watchlistSeasonsAndEpisodes,
                                                       Map<PersistentTvSeason, List<PersistentTvEpisode>> totalSeasonsAndEpisodes) {
        AtomicBoolean hasWatchedAnySeasonPartially = new AtomicBoolean(false);
        List<WatchlistTvSeasonDto> watchlistTvSeasonDtos =  watchlistSeriesAndSeasons.get(persistentTvSeries)
                .stream()
                .map(persistentTvSeason -> {
                    List<TvEpisodeDto> tvEpisodeDtos = watchlistSeasonsAndEpisodes.get(persistentTvSeason)
                            .stream()
                            .map(TvEpisodeDto::from)
                            .sorted(Comparator.comparingInt(TvEpisodeDto::getEpisodeNumber))
                            .collect(Collectors.toList());
                    WatchlistStatus watchlistStatus = tvEpisodeDtos.size() < totalSeasonsAndEpisodes.get(persistentTvSeason).size() ?
                            WatchlistStatus.PARTIAL :
                            WatchlistStatus.COMPLETE;

                    if (watchlistStatus == WatchlistStatus.PARTIAL) {
                        hasWatchedAnySeasonPartially.set(true);
                    }

                    return WatchlistTvSeasonDto.from(persistentTvSeason, tvEpisodeDtos, watchlistStatus);
                })
                .sorted(Comparator.comparing(TvSeasonDto::getSeasonNumber))
                .collect(Collectors.toList());

        boolean hasNotWatchedAllSeasons = watchlistTvSeasonDtos.size() < totalSeriesAndSeasons.get(persistentTvSeries).size();

        WatchlistStatus watchlistStatus = hasNotWatchedAllSeasons || hasWatchedAnySeasonPartially.get() ?
                WatchlistStatus.PARTIAL :
                WatchlistStatus.COMPLETE;
        return WatchlistTvSeriesDto.from(persistentTvSeries, watchlistTvSeasonDtos, watchlistStatus);
    }
}
