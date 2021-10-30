package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
public class TvSeriesFetcherService extends FetcherService {

    private final Set<String> TV_SERIES_WANTED_LANGUAGES = Set.of("en", "he");
    private final int SERIES_LIMIT = 1000;

    private int totalPages = 50;

    public TvSeriesFetcherService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public List<TvSeries> requestPopularSeries() {
        int page = 1;
        List<TvSeries> tvSeriesList = new ArrayList<>();

        while (tvSeriesList.size() < SERIES_LIMIT && page < totalPages) {
            log.info("Querying page number " + page + " for popular series");
            ResponseEntity<PageResult<TvSeries>> response = this.executeRequest(page);

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Error occurred while trying to query popular series. Status code: " + response.getStatusCode() + ", response: " + response.getBody());
                throw new ResponseStatusException(response.getStatusCode());
            } else {
                totalPages = response.getBody().getTotalPages();
                tvSeriesList = appendNewTvSeries(response.getBody().getResults(), tvSeriesList);
                page++;
            }
        }

        log.info("All series size: " + tvSeriesList.size());
        return tvSeriesList;
    }

    private ResponseEntity<PageResult<TvSeries>> executeRequest(int page) {
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

    private List<TvSeries> appendNewTvSeries(List<TvSeries> foundTvSeries, List<TvSeries> accumulatedTvSeries) {
        List<TvSeries> seriesAfterLanguageFilter = foundTvSeries.stream()
                .filter(tvSeries -> TV_SERIES_WANTED_LANGUAGES.contains(tvSeries.getOriginalLanguage()))
                .collect(Collectors.toList());
        log.info("Found " + seriesAfterLanguageFilter.size() + " series matching the language filter: " + TV_SERIES_WANTED_LANGUAGES);

        int appendedAmount = Math.min(seriesAfterLanguageFilter.size(), SERIES_LIMIT - accumulatedTvSeries.size());
        List<TvSeries> appendedSeries = seriesAfterLanguageFilter.stream()
            .limit(appendedAmount)
            .collect(Collectors.toList());
        accumulatedTvSeries.addAll(appendedSeries);

        log.info("Appended " + appendedSeries.size() + ". Accumulated list size: " + accumulatedTvSeries.size());
        log.info("New Series appended: " + appendedSeries);
        return accumulatedTvSeries;
    }
}
