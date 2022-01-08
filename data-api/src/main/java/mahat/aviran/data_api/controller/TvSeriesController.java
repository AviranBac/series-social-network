package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.dtos.TvSeriesDto;
import mahat.aviran.common.entities.dtos.TvSeriesExtendedDto;
import mahat.aviran.data_api.dtos.PageDto;
import mahat.aviran.data_api.dtos.SeriesFilterOptionsDto;
import mahat.aviran.data_api.services.TvSeriesService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/series")
@RequiredArgsConstructor
public class TvSeriesController {

    private final TvSeriesService tvSeriesService;

    @GetMapping()
    @ResponseBody
    public PageDto<TvSeriesDto> getSeriesByFilter(@RequestParam int page,
                                                  @RequestParam(required = false, defaultValue = "") String name,
                                                  @RequestParam(required = false, defaultValue = "") Set<SeriesStatus> seriesStatus,
                                                  @RequestParam(required = false, defaultValue = "") Set<Integer> genreId) {
        return tvSeriesService.getSeriesByFilter(page, name, seriesStatus, genreId);
    }

    @GetMapping("filters")
    @ResponseBody
    public SeriesFilterOptionsDto getSeriesFilterOptions() {
        return tvSeriesService.getSeriesFilterOptions();
    }

    @GetMapping("{seriesId}")
    public ResponseEntity<Object> getSeriesDetailsById(@PathVariable String seriesId) {
        Optional<TvSeriesExtendedDto> tvSeriesExtendedDto = tvSeriesService.getSeriesDetailsById(seriesId);

        return tvSeriesExtendedDto.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Series id " + seriesId + " does not exist"));
    }

    @GetMapping("commonAmongFollowing/{username}")
    @ResponseBody
    public PageDto<TvSeriesDto> getCommonSeriesAmongFollowing(@PathVariable String username,
                                                              @RequestParam int page) {
        return tvSeriesService.getCommonSeriesAmongFollowing(page, username);
    }

    @GetMapping("watched")
    @ResponseBody
    public PageDto<TvSeriesDto> getWatchedSeries(@RequestParam int page,
                                                 @RequestParam Sort.Direction sort) {
        return tvSeriesService.getMostWatchedSeries(page, sort);
    }

    @GetMapping("topRated")
    @ResponseBody
    public PageDto<TvSeriesDto> getTopRatedSeries(@RequestParam int page) {
        return tvSeriesService.getTopRatedSeries(page);
    }

    @GetMapping("popular")
    @ResponseBody
    public PageDto<TvSeriesDto> getMostPopularSeries(@RequestParam int page) {
        return tvSeriesService.getMostPopularSeries(page);
    }
}
