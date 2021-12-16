package mahat.aviran.tvseriesfetcher.entities.raw_request_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TvSeriesFullEntity extends TvSeries {

    private List<TvSeason> seasons;

    public TvSeriesFullEntity(TvSeries tvSeries) {
        super(tvSeries.getFirstAirDate(), tvSeries.getId(), tvSeries.getName(), tvSeries.getGenres(), tvSeries.getOriginalLanguage(),
              tvSeries.getOverview(), tvSeries.getPosterPath(), tvSeries.getPopularity(), tvSeries.getVoteAverage(), tvSeries.getVoteCount(),
              tvSeries.getNumberOfEpisodes(), tvSeries.getNumberOfSeasons(), tvSeries.getStatus());
        this.seasons = new ArrayList<>();
    }
}
