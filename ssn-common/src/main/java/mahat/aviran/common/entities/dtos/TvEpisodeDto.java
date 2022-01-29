package mahat.aviran.common.entities.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvEpisodeDto {

    private String id;
    private String name;
    private int episodeNumber;
    private int seasonNumber;
    private String airDate;
    private String overview;
    private String stillPath;
    private double voteAverage;
    private int voteCount;

    public static TvEpisodeDto from(PersistentTvEpisode persistentTvEpisode) {
        return new TvEpisodeDto()
                .setId(persistentTvEpisode.getId())
                .setName(persistentTvEpisode.getName())
                .setEpisodeNumber(Integer.parseInt(persistentTvEpisode.getEpisodeNumber()))
                .setSeasonNumber(Integer.parseInt(persistentTvEpisode.getSeason().getSeasonNumber()))
                .setAirDate(persistentTvEpisode.getAirDate())
                .setOverview(persistentTvEpisode.getOverview())
                .setStillPath(persistentTvEpisode.getStillPath())
                .setVoteAverage(persistentTvEpisode.getVoteAverage())
                .setVoteCount(persistentTvEpisode.getVoteCount());
    }
}
