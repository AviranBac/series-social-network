package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.dtos.TvSeriesExtendedDto;
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

    @GetMapping("{username}")
    @ResponseBody
    public List<TvSeriesExtendedDto> getUserWatchlist(@PathVariable String username) {
        return this.watchlistService.getUserWatchlist(username);
    }
}
