package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import mahat.aviran.common.repositories.TvSeasonRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesDto;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class WatchlistService {

    private final TvSeriesRepository tvSeriesRepository;
    private final TvSeasonRepository tvSeasonRepository;
    private final TvEpisodeRepository tvEpisodeRepository;

    public List<TvSeriesDto> getAllWatchlistSeries(String username) {
        return this.tvSeriesRepository.getWatchlistTvSeries(Collections.singleton(username))
                .stream()
                .map(TvSeriesDto::from)
                .collect(Collectors.toList());
    }

    public List<TvSeasonDto> getWatchlistSeasonsAndEpisodesBySeries(String username, String seriesId) {
        Map<PersistentTvSeason, List<PersistentTvEpisode>> persistentSeasonsAndEpisodes = this.tvEpisodeRepository.getWatchlistEpisodesBySeries(username, seriesId)
                .stream()
                .collect(Collectors.groupingBy(PersistentTvEpisode::getSeason, Collectors.toList()));

        return persistentSeasonsAndEpisodes.keySet()
                .stream()
                .map(persistentTvSeason -> {
                    List<TvEpisodeDto> tvEpisodeDtos = persistentSeasonsAndEpisodes.get(persistentTvSeason)
                            .stream()
                            .map(TvEpisodeDto::from)
                            .collect(Collectors.toList());
                    return TvSeasonDto.from(persistentTvSeason, tvEpisodeDtos);
                })
                .collect(Collectors.toList());
    }
}
