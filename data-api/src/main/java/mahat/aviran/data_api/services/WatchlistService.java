package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import mahat.aviran.common.repositories.TvSeasonRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesDto;
import mahat.aviran.data_api.dtos.TvSeriesExtendedDto;
import mahat.aviran.data_api.dtos.watchlist.WatchlistStatus;
import mahat.aviran.data_api.dtos.watchlist.WatchlistTvSeasonDto;
import mahat.aviran.data_api.dtos.watchlist.WatchlistTvSeriesDto;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class WatchlistService {

    private final TvSeriesRepository tvSeriesRepository;
    private final TvSeasonRepository tvSeasonRepository;
    private final TvEpisodeRepository tvEpisodeRepository;

    @Transactional
    public List<TvSeriesExtendedDto> getUserWatchlist(String username) {
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
                .map(persistentTvSeries -> this.convertToExtendedDto(
                        persistentTvSeries,
                        watchlistSeriesAndSeasons,
                        totalSeriesAndSeasons,
                        watchlistSeasonsAndEpisodes,
                        totalSeasonsAndEpisodes
                ))
                .sorted(Comparator.comparing(TvSeriesDto::getName))
                .collect(Collectors.toList());
    }

    private TvSeriesExtendedDto convertToExtendedDto(PersistentTvSeries persistentTvSeries,
                                                     Map<PersistentTvSeries, List<PersistentTvSeason>> watchlistSeriesAndSeasons,
                                                     Map<PersistentTvSeries, List<PersistentTvSeason>> totalSeriesAndSeasons,
                                                     Map<PersistentTvSeason, List<PersistentTvEpisode>> watchlistSeasonsAndEpisodes,
                                                     Map<PersistentTvSeason, List<PersistentTvEpisode>> totalSeasonsAndEpisodes) {
        List<TvSeasonDto> tvSeasonDtos =  watchlistSeriesAndSeasons.get(persistentTvSeries)
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

                    return WatchlistTvSeasonDto.from(persistentTvSeason, tvEpisodeDtos, watchlistStatus);
                })
                .sorted(Comparator.comparing(TvSeasonDto::getSeasonNumber))
                .collect(Collectors.toList());

        WatchlistStatus watchlistStatus = tvSeasonDtos.size() < totalSeriesAndSeasons.get(persistentTvSeries).size() ?
                WatchlistStatus.PARTIAL :
                WatchlistStatus.COMPLETE;
        return WatchlistTvSeriesDto.from(persistentTvSeries, tvSeasonDtos, watchlistStatus);
    }
}
