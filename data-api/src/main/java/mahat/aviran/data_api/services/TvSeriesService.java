package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.persistence.PersistentGenre;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.repositories.GenreRepository;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import mahat.aviran.common.repositories.TvSeasonRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesExtendedDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class TvSeriesService {

    private final TvSeriesRepository tvSeriesRepository;
    private final TvSeasonRepository tvSeasonRepository;
    private final TvEpisodeRepository tvEpisodeRepository;
    private final GenreRepository genreRepository;

    public Optional<TvSeriesExtendedDto> getSeriesDetailsById(String seriesId) {
        return this.tvSeriesRepository.findById(seriesId)
                .map(this::convertSeriesToExtendedDto);
    }

    private TvSeriesExtendedDto convertSeriesToExtendedDto(PersistentTvSeries persistentTvSeries) {
        Set<String> seasonIds = tvSeasonRepository.getSeasonIdsBySeriesId(persistentTvSeries.getId());
        Set<String> genreNames = persistentTvSeries.getGenres().stream().map(PersistentGenre::getName).collect(Collectors.toSet());
        List<TvSeasonDto> tvSeasonDtos = tvSeasonRepository.findAllById(seasonIds).stream()
                .map(this::convertSeasonToDto)
                .collect(Collectors.toList());

        return new TvSeriesExtendedDto()
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
                .setGenres(genreNames)
                .setSeasons(tvSeasonDtos);
    }

    private TvSeasonDto convertSeasonToDto(PersistentTvSeason persistentTvSeason) {
        Set<String> episodeIds = tvEpisodeRepository.getEpisodeIdsBySeasonId(persistentTvSeason.getId());
        List<TvEpisodeDto> tvEpisodeDtos = tvEpisodeRepository.findAllById(episodeIds).stream()
                .map(this::convertEpisodeToDto)
                .collect(Collectors.toList());

        return new TvSeasonDto()
                .setId(persistentTvSeason.getId())
                .setName(persistentTvSeason.getName())
                .setSeasonNumber(persistentTvSeason.getSeasonNumber())
                .setAirDate(persistentTvSeason.getAirDate())
                .setOverview(persistentTvSeason.getOverview())
                .setPosterPath(persistentTvSeason.getPosterPath())
                .setEpisodes(tvEpisodeDtos);
    }

    private TvEpisodeDto convertEpisodeToDto(PersistentTvEpisode persistentTvEpisode) {
        return new TvEpisodeDto()
                .setId(persistentTvEpisode.getId())
                .setName(persistentTvEpisode.getName())
                .setEpisodeNumber(persistentTvEpisode.getEpisodeNumber())
                .setSeasonNumber(persistentTvEpisode.getSeason().getSeasonNumber())
                .setAirDate(persistentTvEpisode.getAirDate())
                .setOverview(persistentTvEpisode.getOverview())
                .setStillPath(persistentTvEpisode.getStillPath())
                .setVoteAverage(persistentTvEpisode.getVoteAverage())
                .setVoteCount(persistentTvEpisode.getVoteCount());
    }
}
