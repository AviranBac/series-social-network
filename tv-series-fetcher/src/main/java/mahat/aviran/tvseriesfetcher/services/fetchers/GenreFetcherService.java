package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.Genre;
import mahat.aviran.tvseriesfetcher.entities.GenreResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@Log4j2
public class GenreFetcherService extends FetcherService {

    public GenreFetcherService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public List<Genre> requestGenres() {
        log.info("Requesting all genres from TMDB");
        ResponseEntity<GenreResponse> response = this.executeRequest();

        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Error occurred while trying to request genres. Status code: " + response.getStatusCode() + ", response: " + response.getBody());
            throw new ResponseStatusException(response.getStatusCode());
        }

        List<Genre> genres = response.getBody().getGenres();
        log.info("Got " + genres.size() + " genres from TMDB: " + genres);
        return genres;
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
