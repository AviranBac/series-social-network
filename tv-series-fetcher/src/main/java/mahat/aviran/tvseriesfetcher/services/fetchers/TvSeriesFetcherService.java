package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.PopularSeriesMinimalIdentifier;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.PageResult;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeries;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeriesFullEntity;
import mahat.aviran.tvseriesfetcher.services.EntitySaverService;
import mahat.aviran.tvseriesfetcher.services.MapperService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Log4j2
public class TvSeriesFetcherService extends FetcherService {

    private final TvSeasonFetcherService tvSeasonFetcherService;
    private final MapperService mapperService;
    private final Set<String> TV_SERIES_WANTED_LANGUAGES = Set.of("en", "he");
    private final int SERIES_LIMIT = 30;

    private int totalPages = 50;

    public TvSeriesFetcherService(RestTemplate restTemplate,
                                  TvSeasonFetcherService tvSeasonFetcherService,
                                  MapperService mapperService) {
        super(restTemplate);
        this.tvSeasonFetcherService = tvSeasonFetcherService;
        this.mapperService = mapperService;
    }

    public List<TvSeriesFullEntity> requestPopularSeries() {
        int page = 1;
        List<TvSeriesFullEntity> tvSeriesList = new ArrayList<>();

        while (tvSeriesList.size() < SERIES_LIMIT && page < totalPages) {
            log.info("Querying page number " + page + " for popular series");
            ResponseEntity<PageResult<PopularSeriesMinimalIdentifier>> response = this.executePopularSeriesRequest(page);

            totalPages = response.getBody().getTotalPages();

            List<PopularSeriesMinimalIdentifier> filteredSeriesIds = this.filterByLanguage(response.getBody().getResults());
            log.info("Found " + filteredSeriesIds.size() + " series matching the language filter");

            tvSeriesList = appendNewTvSeries(filteredSeriesIds, tvSeriesList);
            page++;
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
        return seriesIdentifier.stream()
                .filter(tvSeries -> TV_SERIES_WANTED_LANGUAGES.contains(tvSeries.getOriginalLanguage()))
                .collect(Collectors.toList());
    }

    private List<TvSeriesFullEntity> appendNewTvSeries(List<PopularSeriesMinimalIdentifier> newSeriesIds,
                                                       List<TvSeriesFullEntity> accumulatedTvSeries) {
        List<TvSeriesFullEntity> appendedSeries = this.fetchSeriesFullEntity(newSeriesIds, accumulatedTvSeries);
        accumulatedTvSeries.addAll(appendedSeries);

        log.info("Appended " + appendedSeries.size() + ". Accumulated list size: " + accumulatedTvSeries.size());
        return accumulatedTvSeries;
    }

    private List<TvSeriesFullEntity> fetchSeriesFullEntity(List<PopularSeriesMinimalIdentifier> seriesIds,
                                                           List<TvSeriesFullEntity> accumulatedSeries) {
        int limit = Math.min(seriesIds.size(), SERIES_LIMIT - accumulatedSeries.size());

        return seriesIds.stream()
                .map(PopularSeriesMinimalIdentifier::getId)
                .flatMap(id -> {
                    try {
                        ResponseEntity<TvSeries> seriesResponse = this.executeTvSeriesRequest(id);
                        return Stream.of(seriesResponse.getBody());
                    } catch (HttpClientErrorException e) {
                        log.error("Could not request tv series id: " + id);
                        return Stream.empty();
                    }
                })
                .limit(limit)
                .map(TvSeriesFullEntity::new)
                .peek(tvSeries -> tvSeries.setSeasons(tvSeasonFetcherService.requestSeasons(tvSeries)))
                .peek(tvSeries -> mapperService.getSeriesSource().onNext(tvSeries))
                .collect(Collectors.toList());
    }
}
