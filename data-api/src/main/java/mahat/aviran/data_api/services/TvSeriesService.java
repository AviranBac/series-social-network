package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import mahat.aviran.common.repositories.TvSeasonRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.data_api.dtos.PageDto;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesDto;
import mahat.aviran.data_api.dtos.TvSeriesExtendedDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static mahat.aviran.common.repositories.TvSeriesRepository.*;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class TvSeriesService {

    private final TvSeriesRepository tvSeriesRepository;
    private final TvSeasonRepository tvSeasonRepository;
    private final TvEpisodeRepository tvEpisodeRepository;
    private final FollowRepository followRepository;
    private final int PAGE_SIZE = 10;

    @Transactional
    public PageDto<TvSeriesDto> getSeriesByFilter(int page,
                                                  String nameCriteria,
                                                  Set<SeriesStatus> seriesStatusCriteria,
                                                  Set<Integer> genreIdCriteria) {
        Specification<PersistentTvSeries> specification = nameStartsWith(nameCriteria)
                .and(seriesStatusIn(seriesStatusCriteria))
                .and(genreIdIn(genreIdCriteria));

        Page<TvSeriesDto> pageResult = this.tvSeriesRepository
                .findAll(specification, PageRequest.of(page, PAGE_SIZE))
                .map(TvSeriesDto::from);

        return PageDto.from(pageResult);
    }

    @Transactional
    public Optional<TvSeriesExtendedDto> getSeriesDetailsById(String seriesId) {
        return this.tvSeriesRepository.findById(seriesId)
                .map(this::convertSeriesToExtendedDto);
    }

    @Transactional
    public PageDto<TvSeriesDto> getCommonSeriesAmongFollowing(int page, String username) {
        PersistentUser tempPersistentUser = new PersistentUser().setUserName(username);

        Set<String> followingUsernames = this.followRepository.findAllByUsernameFrom(tempPersistentUser).stream()
                .map(FollowRepository.Following::getUsernameTo)
                .map(PersistentUser::getUserName)
                .collect(Collectors.toSet());

        Page<TvSeriesDto> commonSeries = this.tvSeriesRepository.getCommonSeriesAmongUsers(
                followingUsernames,
                PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "common_series.count"))
        ).map(TvSeriesDto::from);

        return PageDto.from(commonSeries);
    }

    @Transactional
    public PageDto<TvSeriesDto> getMostWatchedSeries(int page, Sort.Direction direction) {
        Page<TvSeriesDto> mostWatchedSeries = this.tvSeriesRepository.getWatchedSeries(
                PageRequest.of(page, PAGE_SIZE, Sort.by(direction, "common_series.count"))
        ).map(TvSeriesDto::from);

        return PageDto.from(mostWatchedSeries);
    }

    private TvSeriesExtendedDto convertSeriesToExtendedDto(PersistentTvSeries persistentTvSeries) {
        List<TvSeasonDto> tvSeasonDtos = tvSeasonRepository.getSeasonsBySeriesId(persistentTvSeries.getId()).stream()
                .map(persistentSeason -> TvSeasonDto.from(persistentSeason, this.generateTvEpisodeDtos(persistentSeason)))
                .collect(Collectors.toList());

        return new TvSeriesExtendedDto(TvSeriesDto.from(persistentTvSeries))
                .setSeasons(tvSeasonDtos);
    }

    private List<TvEpisodeDto> generateTvEpisodeDtos(PersistentTvSeason persistentTvSeason) {
        return tvEpisodeRepository.getEpisodesBySeasonId(persistentTvSeason.getId()).stream()
                .map(TvEpisodeDto::from)
                .collect(Collectors.toList());
    }
}
