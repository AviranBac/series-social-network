package mahat.aviran.user_actions_service.api;

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
import mahat.aviran.user_actions_service.entities.WatchlistRecordDetails;
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

    private PersistentTvEpisode episodeInDB = new PersistentTvEpisode()
            .setId(UUID.randomUUID().toString())
            .setName("TestEpisode")
            .setEpisodeNumber("1")
            .setAirDate("2022-02-04")
            .setOverview("someOverviewEpisode")
            .setStillPath("http://bla")
            .setVoteAverage(10)
            .setVoteCount(11);

    private PersistentTvSeason seasonInDB = new PersistentTvSeason()
            .setId(UUID.randomUUID().toString())
            .setName("TestSeason")
            .setSeasonNumber("1")
            .setAirDate("2022-02-04")
            .setOverview("someOverviewSeason")
            .setPosterPath("http://blabla")
            .setEpisodes(Set.of(episodeInDB));

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
            .setLastName("Bac");

    @BeforeAll
    public void initializeData() {
        episodeInDB.setSeason(seasonInDB);
        seasonInDB.setTvSeries(seriesInDB);

        tvSeriesRepository.save(seriesInDB);
        userRepository.save(userInDB);
    }

    @AfterAll
    public void removeSeries() {
        tvSeriesRepository.delete(seriesInDB);
        userRepository.delete(userInDB);
    }

    @Test
    public void updateWatchlist_expectUpdate() {
        String url = "http://localhost:" + port + "/watchlist";
        WatchlistRecordDetails requestBody = new WatchlistRecordDetails()
                .setAction(WatchlistRecordDetails.Action.ADD)
                .setEntityType(WatchlistRecordDetails.EntityType.EPISODE)
                .setUsername(userInDB.getUserName())
                .setEntityId(episodeInDB.getId());

        TvEpisodeDto expectedEpisode = new TvEpisodeDto()
                .setId(episodeInDB.getId())
                .setName(episodeInDB.getName())
                .setEpisodeNumber(1)
                .setSeasonNumber(1)
                .setAirDate(episodeInDB.getAirDate())
                .setOverview(episodeInDB.getOverview())
                .setStillPath(episodeInDB.getStillPath())
                .setVoteAverage(episodeInDB.getVoteAverage())
                .setVoteCount(episodeInDB.getVoteCount());

        WatchlistTvSeasonDto expectedSeason = (WatchlistTvSeasonDto) new WatchlistTvSeasonDto()
                .setWatchlistStatus(WatchlistStatus.COMPLETE)
                .setId(seasonInDB.getId())
                .setName(seasonInDB.getName())
                .setSeasonNumber(1)
                .setAirDate(seasonInDB.getAirDate())
                .setOverview(seasonInDB.getOverview())
                .setPosterPath(seasonInDB.getPosterPath())
                .setEpisodes(List.of(expectedEpisode));

        WatchlistTvSeriesDto expectedSeries = new WatchlistTvSeriesDto()
                .setWatchlistStatus(WatchlistStatus.COMPLETE);
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

        List<WatchlistTvSeriesDto> expectedWatchlistAfterAddition = List.of(expectedSeries);
        List<WatchlistTvSeriesDto> actualWatchlistAfterAddition = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                new ParameterizedTypeReference<List<WatchlistTvSeriesDto>>() {}
        ).getBody();
        assertEquals(expectedWatchlistAfterAddition, actualWatchlistAfterAddition);

        requestBody = new WatchlistRecordDetails()
                .setAction(WatchlistRecordDetails.Action.REMOVE)
                .setEntityType(WatchlistRecordDetails.EntityType.EPISODE)
                .setUsername(userInDB.getUserName())
                .setEntityId(episodeInDB.getId());

        expectedWatchlistAfterAddition = List.of();
        actualWatchlistAfterAddition = restTemplate.exchange(
                url,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                new ParameterizedTypeReference<List<WatchlistTvSeriesDto>>() {}
        ).getBody();
        assertEquals(expectedWatchlistAfterAddition, actualWatchlistAfterAddition);
    }
}
