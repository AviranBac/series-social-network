package mahat.aviran.tvseriesfetcher.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.services.fetchers.GenreFetcherService;
import mahat.aviran.tvseriesfetcher.services.fetchers.TvSeriesFetcherService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ManagerService {

    private final GenreFetcherService genreFetcherService;
    private final TvSeriesFetcherService tvSeriesFetcherService;

    public void fetchAll() {
        this.genreFetcherService.requestGenres();
        this.tvSeriesFetcherService.requestPopularSeries();
    }
}
