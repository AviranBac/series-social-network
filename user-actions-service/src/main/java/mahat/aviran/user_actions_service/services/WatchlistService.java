package mahat.aviran.user_actions_service.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.TvSeriesExtendedDto;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.helpers.WatchlistBuilder;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import mahat.aviran.common.repositories.TvSeasonRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.user_actions_service.entities.WatchlistRecordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Set;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class WatchlistService {

    private final UserRepository userRepository;
    private final TvSeriesRepository tvSeriesRepository;
    private final TvSeasonRepository tvSeasonRepository;
    private final TvEpisodeRepository tvEpisodeRepository;
    private final WatchlistBuilder watchlistBuilder;

    @Transactional
    public List<TvSeriesExtendedDto> addToWatchlist(String username, WatchlistRecordDetails.EntityType entityType, String entityId) {
        this.validateInput(username, entityType, entityId, false);
        List<PersistentTvEpisode> episodesToAdd = this.getTvEpisodesByEntity(entityType, entityId);

        PersistentUser updatedUser = this.userRepository.getById(username);
        updatedUser.getWatchlistRecords().addAll(episodesToAdd);
        this.userRepository.save(updatedUser);

        return this.watchlistBuilder.getUserWatchlist(username);
    }

    @Transactional
    public List<TvSeriesExtendedDto> removeFromWatchlist(String username, WatchlistRecordDetails.EntityType entityType, String entityId) {
        this.validateInput(username, entityType, entityId, true);
        List<PersistentTvEpisode> episodesToRemove = this.getTvEpisodesByEntity(entityType, entityId);

        PersistentUser updatedUser = this.userRepository.getById(username);
        updatedUser.getWatchlistRecords().removeAll(episodesToRemove);
        this.userRepository.save(updatedUser);

        return this.watchlistBuilder.getUserWatchlist(username);
    }

    private void validateInput(String username, WatchlistRecordDetails.EntityType entityType, String entityId, boolean shouldExist) {
        if (!this.userRepository.existsById(username)) {
            throw new EntityNotFoundException();
        }

        JpaRepository targetRepository;

        switch (entityType) {
            case SERIES: targetRepository = tvSeriesRepository; break;
            case SEASON: targetRepository = tvSeasonRepository; break;
            case EPISODE: targetRepository = tvEpisodeRepository; break;
            default: throw new InputMismatchException();
        }

        if (!targetRepository.existsById(entityId)) {
            throw new EntityNotFoundException();
        }
    }

    private List<PersistentTvEpisode> getTvEpisodesByEntity(WatchlistRecordDetails.EntityType entityType, String entityId) {
        switch (entityType) {
            case SERIES:  return this.tvEpisodeRepository.getEpisodeIdsBySeriesIds(Set.of(entityId));
            case SEASON:  return this.tvEpisodeRepository.getEpisodesBySeasonId(entityId);
            case EPISODE: return this.tvEpisodeRepository.findAllById(Set.of(entityId));
            default: return List.of();
        }
    }
}
