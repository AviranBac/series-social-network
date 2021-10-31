package mahat.aviran.tvseriesfetcher.entities.persistence_mappers;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeason;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TvSeasonMapper {

    private final TvEpisodeMapper tvEpisodeMapper;

    public PersistentTvSeason toPersistent(TvSeason tvSeason, PersistentTvSeries tvSeries) {
        PersistentTvSeason persistentTvSeason = new PersistentTvSeason()
                .setId(tvSeason.getId())
                .setName(tvSeason.getName())
                .setSeasonNumber(tvSeason.getSeasonNumber())
                .setAirDate(tvSeason.getAirDate())
                .setOverview(tvSeason.getOverview())
                .setPosterPath(tvSeason.getPosterPath())
                .setTvSeries(tvSeries);

        Set<PersistentTvEpisode> episodes = tvSeason.getEpisodes().stream()
            .map(tvEpisode -> tvEpisodeMapper.toPersistent(tvEpisode, persistentTvSeason))
            .collect(Collectors.toSet());
        persistentTvSeason.setEpisodes(episodes);

        return persistentTvSeason;
    }
}
