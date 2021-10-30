package mahat.aviran.tvseriesfetcher.services.fetchers;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class TvSeasonFetcherService extends FetcherService {

    public TvSeasonFetcherService(RestTemplate restTemplate) {
        super(restTemplate);
    }
}
