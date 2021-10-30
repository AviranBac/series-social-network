package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.PopularSeriesMinimalIdentifier;
import mahat.aviran.tvseriesfetcher.entities.PageResult;
import mahat.aviran.tvseriesfetcher.entities.TvSeries;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TvSeriesFetcherService extends FetcherService {

    private final Set<String> TV_SERIES_WANTED_LANGUAGES = Set.of("en", "he");
    private final int SERIES_LIMIT = 100;

    private int totalPages = 50;

    public TvSeriesFetcherService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public List<TvSeries> requestPopularSeries() {
        int page = 1;
        List<TvSeries> tvSeriesList = new ArrayList<>();

        while (tvSeriesList.size() < SERIES_LIMIT && page < totalPages) {
            log.info("Querying page number " + page + " for popular series");
            ResponseEntity<PageResult<PopularSeriesMinimalIdentifier>> response = this.executePopularSeriesRequest(page);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error occurred while trying to query popular series. Status code: " + response.getStatusCode() + ", response: " + response.getBody());
                throw new ResponseStatusException(response.getStatusCode());
            } else {
                totalPages = response.getBody().getTotalPages();

                List<PopularSeriesMinimalIdentifier> filteredSeriesIds = this.filterByLanguage(response.getBody().getResults());
                log.info("Found " + filteredSeriesIds.size() + " series matching the language filter: " + TV_SERIES_WANTED_LANGUAGES);

                List<TvSeries> fetchedSeries = filteredSeriesIds.stream()
                        .map(PopularSeriesMinimalIdentifier::getId)
                        .flatMap(id -> {
                            ResponseEntity<TvSeries> seriesResponse = this.executeTvSeriesRequest(id);
                            return response.getStatusCode() != HttpStatus.OK ? Stream.empty() : Stream.of(seriesResponse.getBody());
                        })
                        .collect(Collectors.toList());
                log.info("Fetched " + fetchedSeries.size() + " series");

                tvSeriesList = appendNewTvSeries(fetchedSeries, tvSeriesList);
                page++;
            }
        }

        log.info("All series size: " + tvSeriesList.size());
        return tvSeriesList;
    }

    private ResponseEntity<PageResult<PopularSeriesMinimalIdentifier>> executePopularSeriesRequest(int page) {
        URI uri = UriComponentsBuilder.fromHttpUrl(this.websiteUrl)
                .path("/tv/popular")
                .queryParam("api_key", this.apiKey)
                .queryParam("language", LANGUAGE_LOCALE)
                .queryParam("page", page)
                .build().toUri();
        return this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<TvSeries> executeTvSeriesRequest(int id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(this.websiteUrl)
                .path("/tv/" + id)
                .queryParam("api_key", this.apiKey)
                .queryParam("language", LANGUAGE_LOCALE)
                .build().toUri();
        return this.restTemplate.getForEntity(uri, TvSeries.class);
    }

    private List<PopularSeriesMinimalIdentifier> filterByLanguage(List<PopularSeriesMinimalIdentifier> seriesIdentifier) {
        System.out.println(seriesIdentifier);
        return seriesIdentifier.stream()
                .filter(tvSeries -> TV_SERIES_WANTED_LANGUAGES.contains(tvSeries.getOriginalLanguage()))
                .collect(Collectors.toList());
    }

    private List<TvSeries> appendNewTvSeries(List<TvSeries> foundTvSeries, List<TvSeries> accumulatedTvSeries) {
        int appendedAmount = Math.min(foundTvSeries.size(), SERIES_LIMIT - accumulatedTvSeries.size());
        List<TvSeries> appendedSeries = foundTvSeries.stream()
            .limit(appendedAmount)
            .collect(Collectors.toList());
        accumulatedTvSeries.addAll(appendedSeries);

        log.info("Appended " + appendedSeries.size() + ". Accumulated list size: " + accumulatedTvSeries.size());
        log.info("New Series appended: " + appendedSeries);
        return accumulatedTvSeries;
    }
}
