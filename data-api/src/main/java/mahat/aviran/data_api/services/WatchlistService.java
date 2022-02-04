package mahat.aviran.data_api.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistTvSeriesDto;
import mahat.aviran.common.helpers.WatchlistBuilder;
import mahat.aviran.common.repositories.TvEpisodeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class WatchlistService {

    private final WatchlistBuilder watchlistBuilder;
    private final TvEpisodeRepository tvEpisodeRepository;

    @Transactional
    public List<WatchlistTvSeriesDto> getUserWatchlist(String username) {
        return this.watchlistBuilder.getUserWatchlist(username);
    }
}
