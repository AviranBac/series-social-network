package mahat.aviran.data_api.api;

import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.dtos.TvEpisodeDto;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistStatus;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistTvSeasonDto;
import mahat.aviran.common.entities.dtos.watchlist.WatchlistTvSeriesDto;
import mahat.aviran.common.entities.persistence.PersistentTvEpisode;
import mahat.aviran.common.entities.persistence.PersistentTvSeason;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.common.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "test")
public class WatchlistControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TvSeriesRepository tvSeriesRepository;

    private PersistentTvEpisode episode1InDB = new PersistentTvEpisode()
            .setId(UUID.randomUUID().toString())
            .setName("TestEpisode1")
            .setEpisodeNumber("1")
            .setAirDate("2022-02-04")
            .setOverview("someOverviewEpisode1")
            .setStillPath("http://bla1")
            .setVoteAverage(10)
            .setVoteCount(11);

    private PersistentTvEpisode episode2InDB = new PersistentTvEpisode()
            .setId(UUID.randomUUID().toString())
            .setName("TestEpisode2")
            .setEpisodeNumber("2")
            .setAirDate("2022-02-05")
            .setOverview("someOverviewEpisode2")
            .setStillPath("http://bla2")
            .setVoteAverage(1)
            .setVoteCount(2);

    private PersistentTvSeason seasonInDB = new PersistentTvSeason()
            .setId(UUID.randomUUID().toString())
            .setName("TestSeason")
            .setSeasonNumber("1")
            .setAirDate("2022-02-04")
            .setOverview("someOverviewSeason")
            .setPosterPath("http://blabla")
            .setEpisodes(Set.of(episode1InDB, episode2InDB));

    private PersistentTvSeries seriesInDB = new PersistentTvSeries()
            .setId(UUID.randomUUID().toString())
            .setName("TestSeries")
            .setFirstAirDate("2022-02-04")
            .setOriginalLanguage("English")
            .setOverview("someOverviewSeries")
            .setPosterPath("http://blablabla")
            .setPopularity(10)
            .setVoteAverage(9)
            .setVoteCount(8)
            .setNumberOfEpisodes(1)
            .setNumberOfSeasons(1)
            .setStatus(SeriesStatus.ENDED)
            .setGenres(Set.of())
            .setSeasons(Set.of(seasonInDB));

    private PersistentUser userInDB = new PersistentUser()
            .setUserName("Test_AviranB")
            .setFirstName("Avi")
            .setLastName("Bac")
            .setWatchlistRecords(Set.of(episode1InDB));

    @BeforeAll
    public void addSeries() {
        seasonInDB.setTvSeries(seriesInDB);
        episode1InDB.setSeason(seasonInDB);
        episode2InDB.setSeason(seasonInDB);

        tvSeriesRepository.save(seriesInDB);
        userRepository.save(userInDB);
    }

    @AfterAll
    public void removeSeries() {
        userRepository.delete(userInDB);
        tvSeriesRepository.delete(seriesInDB);
    }

    @Test
    public void getUserWatchlist_expectOneSeriesOneSeasonOneEpisode() {
        TvEpisodeDto expectedEpisode1 = new TvEpisodeDto()
                .setId(episode1InDB.getId())
                .setName(episode1InDB.getName())
                .setEpisodeNumber(1)
                .setSeasonNumber(1)
                .setAirDate(episode1InDB.getAirDate())
                .setOverview(episode1InDB.getOverview())
                .setStillPath(episode1InDB.getStillPath())
                .setVoteAverage(episode1InDB.getVoteAverage())
                .setVoteCount(episode1InDB.getVoteCount());

        WatchlistTvSeasonDto expectedSeason = (WatchlistTvSeasonDto) new WatchlistTvSeasonDto()
                .setWatchlistStatus(WatchlistStatus.PARTIAL)
                .setId(seasonInDB.getId())
                .setName(seasonInDB.getName())
                .setSeasonNumber(1)
                .setAirDate(seasonInDB.getAirDate())
                .setOverview(seasonInDB.getOverview())
                .setPosterPath(seasonInDB.getPosterPath())
                .setEpisodes(List.of(expectedEpisode1));

        WatchlistTvSeriesDto expectedSeries = new WatchlistTvSeriesDto()
                .setWatchlistStatus(WatchlistStatus.PARTIAL);
        expectedSeries.setId(seriesInDB.getId());
        expectedSeries.setName(seriesInDB.getName());
        expectedSeries.setFirstAirDate(seriesInDB.getFirstAirDate());
        expectedSeries.setOriginalLanguage(seriesInDB.getOriginalLanguage());
        expectedSeries.setOverview(seriesInDB.getOverview());
        expectedSeries.setPosterPath(seriesInDB.getPosterPath());
        expectedSeries.setPopularity(seriesInDB.getPopularity());
        expectedSeries.setVoteAverage(seriesInDB.getVoteAverage());
        expectedSeries.setVoteCount(seriesInDB.getVoteCount());
        expectedSeries.setNumberOfEpisodes(seriesInDB.getNumberOfEpisodes());
        expectedSeries.setNumberOfSeasons(seriesInDB.getNumberOfSeasons());
        expectedSeries.setStatus(seriesInDB.getStatus());
        expectedSeries.setGenres(Set.of());
        expectedSeries.setSeasons(List.of(expectedSeason));

        String url = "http://localhost:" + port + "/watchlist/Test_AviranB";
        ResponseEntity<List<WatchlistTvSeriesDto>> actual =
                this.restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<WatchlistTvSeriesDto>>() {});

        assertEquals(List.of(expectedSeries), actual.getBody());
    }
}
