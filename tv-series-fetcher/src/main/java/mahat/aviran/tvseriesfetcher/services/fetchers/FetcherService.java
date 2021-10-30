package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Log4j2
public abstract class FetcherService {

    protected final String LANGUAGE_LOCALE = "he-IL";
    protected final RestTemplate restTemplate;

    @Value("${apiRequest.websiteUrl}")
    protected String websiteUrl;
    @Value("${apiRequest.apiKey}")
    protected String apiKey;
}
