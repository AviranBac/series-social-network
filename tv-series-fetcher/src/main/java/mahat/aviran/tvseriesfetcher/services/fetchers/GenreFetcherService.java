package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.Genre;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.GenreResponse;
import mahat.aviran.tvseriesfetcher.services.MapperService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Log4j2
public class GenreFetcherService extends FetcherService {

    private final MapperService mapperService;

    public GenreFetcherService(RestTemplate restTemplate,
                               MapperService mapperService) {
        super(restTemplate);
        this.mapperService = mapperService;
    }

    public void requestGenres() {
        log.info("Requesting all genres from TMDB");
        ResponseEntity<GenreResponse> response = this.executeRequest();

        List<Genre> genres = response.getBody().getGenres();
        log.info("Got " + genres.size() + " genres from TMDB: " + genres);

        this.mapperService.getGenreSource().onNext(genres);
    }

    private ResponseEntity<GenreResponse> executeRequest() {
        URI uri = UriComponentsBuilder.fromHttpUrl(this.websiteUrl)
                .path("/genre/tv/list")
                .queryParam("api_key", this.apiKey)
                .queryParam("language", LANGUAGE_LOCALE)
                .build().toUri();
        return this.restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
    }
}
