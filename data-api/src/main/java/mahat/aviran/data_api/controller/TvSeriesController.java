package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.data_api.dtos.TvSeriesExtendedDto;
import mahat.aviran.data_api.services.TvSeriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/series")
@RequiredArgsConstructor
public class TvSeriesController {

    private final TvSeriesService tvSeriesService;

    @GetMapping("{seriesId}")
    public ResponseEntity<Object> getSeriesDetailsById(@PathVariable String seriesId) {
        Optional<TvSeriesExtendedDto> tvSeriesExtendedDto = tvSeriesService.getSeriesDetailsById(seriesId);

        return tvSeriesExtendedDto.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Series id " + seriesId + " does not exist"));
    }
}
