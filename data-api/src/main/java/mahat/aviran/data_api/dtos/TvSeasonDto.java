package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class TvSeasonDto {

    private String id;
    private String name;
    private int seasonNumber;
    private String airDate;
    private String overview;
    private String posterPath;
    private List<TvEpisodeDto> episodes;

    public static TvSeasonDto from(PersistentTvSeason persistentTvSeason, List<TvEpisodeDto> tvEpisodeDtos) {
        return new TvSeasonDto()
                .setId(persistentTvSeason.getId())
                .setName(persistentTvSeason.getName())
                .setSeasonNumber(Integer.parseInt(persistentTvSeason.getSeasonNumber()))
                .setAirDate(persistentTvSeason.getAirDate())
                .setOverview(persistentTvSeason.getOverview())
                .setPosterPath(persistentTvSeason.getPosterPath())
                .setEpisodes(tvEpisodeDtos);
    }

    public TvSeasonDto sortEpisodes() {
        this.episodes = this.episodes.stream()
                .sorted(Comparator.comparingInt(TvEpisodeDto::getEpisodeNumber))
                .collect(Collectors.toList());
        return this;
    }
}
