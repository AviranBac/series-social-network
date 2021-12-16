package mahat.aviran.tvseriesfetcher.entities.persistence_mappers;

import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvEpisode;
import org.springframework.stereotype.Component;

@Component
public class TvEpisodeMapper {

    public PersistentTvEpisode toPersistent(TvEpisode tvEpisode, PersistentTvSeason season) {
        return new PersistentTvEpisode()
                .setId(tvEpisode.getId())
                .setName(tvEpisode.getName())
                .setEpisodeNumber(tvEpisode.getEpisodeNumber())
                .setAirDate(tvEpisode.getAirDate())
                .setOverview(tvEpisode.getOverview())
                .setStillPath(tvEpisode.getStillPath())
                .setVoteAverage(tvEpisode.getVoteAverage())
                .setVoteCount(tvEpisode.getVoteCount())
                .setSeason(season);
    }
}
