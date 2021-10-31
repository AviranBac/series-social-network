package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeason;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeries;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Log4j2
public class TvSeasonFetcherService extends FetcherService {

    public TvSeasonFetcherService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public List<TvSeason> requestSeasons(TvSeries tvSeries) {
        return IntStream.range(1, tvSeries.getNumberOfSeasons() + 1)
                .boxed()
                .flatMap(seasonNumber -> this.requestSeason(tvSeries, seasonNumber))
                .collect(Collectors.toList());
    }

    public Stream<TvSeason> requestSeason(TvSeries tvSeries, int seasonNumber) {
        log.info("Requesting season number " + seasonNumber + " for " + tvSeries.getName() + " (id: " + tvSeries.getId() + ")");

        try {
            ResponseEntity<TvSeason> response = this.executeTvSeasonRequest(tvSeries.getId(), seasonNumber);
            log.info("Got season number " + seasonNumber + " for " + tvSeries.getName() + " (id: " + tvSeries.getId() + ")");
            return Stream.of(response.getBody());
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.NotFound) {
                log.error("Season number " + seasonNumber + " for " + tvSeries.getName() + " (id: " + tvSeries.getId() + ") not found (404)");
            } else {
                log.error("Error occurred while trying to query season number " + seasonNumber + " for " + tvSeries.getName() +
                          " (id: " + tvSeries.getId() + ")", e);
            }

            return Stream.empty();
        }
    }

    private ResponseEntity<TvSeason> executeTvSeasonRequest(String seriesId, int seasonNumber) {
        URI uri = UriComponentsBuilder.fromHttpUrl(this.websiteUrl)
                .path("/tv/" + seriesId + "/season/" + seasonNumber)
                .queryParam("api_key", this.apiKey)
                .queryParam("language", LANGUAGE_LOCALE)
                .build().toUri();
        return this.restTemplate.getForEntity(uri, TvSeason.class);
    }
}
