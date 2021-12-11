package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.data_api.dtos.TvSeasonDto;
import mahat.aviran.data_api.dtos.TvSeriesDto;
import mahat.aviran.data_api.services.WatchlistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @GetMapping("{username}/series")
    @ResponseBody
    public List<TvSeriesDto> getAllWatchlistSeries(@PathVariable String username) {
        return this.watchlistService.getAllWatchlistSeries(username);
    }

    @GetMapping("{username}/series/{seriesId}")
    @ResponseBody
    public List<TvSeasonDto> getWatchlistSeasonsAndEpisodesBySeries(@PathVariable String username, @PathVariable String seriesId) {
        return this.watchlistService.getWatchlistSeasonsAndEpisodesBySeries(username, seriesId);
    }
}
