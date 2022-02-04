package mahat.aviran.data_api.api;

import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.dtos.TvSeriesDto;
import mahat.aviran.common.entities.persistence.*;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.GenreRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.data_api.dtos.PageDto;
import org.junit.jupiter.api.*;
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
public class TvSeriesControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TvSeriesRepository tvSeriesRepository;

    private Set<PersistentGenre> genres = Set.of(
            new PersistentGenre(1, "Genre A"),
            new PersistentGenre(2, "Genre B")
    );

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
            .setGenres(genres)
            .setSeasons(Set.of(seasonInDB));

    private TvSeriesDto expectedSeriesDto = new TvSeriesDto()
            .setId(seriesInDB.getId())
            .setName(seriesInDB.getName())
            .setFirstAirDate(seriesInDB.getFirstAirDate())
            .setOriginalLanguage(seriesInDB.getOriginalLanguage())
            .setOverview(seriesInDB.getOverview())
            .setPosterPath(seriesInDB.getPosterPath())
            .setPopularity(seriesInDB.getPopularity())
            .setVoteAverage(seriesInDB.getVoteAverage())
            .setVoteCount(seriesInDB.getVoteCount())
            .setNumberOfEpisodes(seriesInDB.getNumberOfEpisodes())
            .setNumberOfSeasons(seriesInDB.getNumberOfSeasons())
            .setStatus(seriesInDB.getStatus())
            .setGenres(Set.of("Genre A", "Genre B"));

    @BeforeAll
    public void addSeries() {
        episode1InDB.setSeason(seasonInDB);
        episode2InDB.setSeason(seasonInDB);
        seasonInDB.setTvSeries(seriesInDB);

        genreRepository.saveAll(genres);
        tvSeriesRepository.save(seriesInDB);
    }

    @AfterAll
    public void removeSeries() {
        tvSeriesRepository.delete(seriesInDB);
        genreRepository.deleteAllInBatch(genres);
    }

    @Test
    public void seriesInDatabase_expectSeriesFoundInSearch() {
        PageDto<TvSeriesDto> expectedResult = PageDto.from(expectedSeriesDto);

        String url = "http://localhost:" + port + "/series?page=0&name=TestSeries&seriesStatus=ENDED&genreId=1";
        ResponseEntity<PageDto<TvSeriesDto>> actual =
                this.restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageDto<TvSeriesDto>>() {});
        assertEquals(expectedResult, actual.getBody());
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class getCommonSeriesAmongFollowing {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private FollowRepository followRepository;

        private PersistentUser userInDB = new PersistentUser()
                .setUserName("Test_AviranB")
                .setFirstName("Avi")
                .setLastName("Bac");

        private PersistentUser otherUserInDB = new PersistentUser()
                .setUserName("Test_AviranB2")
                .setFirstName("Avi")
                .setLastName("Bac")
                .setWatchlistRecords(Set.of(episode1InDB));

        private PersistentFollow follow = new PersistentFollow()
                .setId(-1)
                .setUsernameFrom(userInDB)
                .setUsernameTo(otherUserInDB);

        @BeforeAll
        public void insertUsers() {
            userRepository.saveAll(List.of(userInDB, otherUserInDB));
        }

        @AfterAll
        public void removeUsersAndFollow() {
            followRepository.delete(follow);
            userRepository.deleteAllInBatch(List.of(userInDB, otherUserInDB));
        }

        @Test
        public void assertFollowAffectsReturnedSeries() {
            PageDto<TvSeriesDto> expectedInitialCommonSeries = PageDto.from();
            String url = "http://localhost:" + port + "/series/commonAmongFollowing/Test_AviranB?page=0";
            ResponseEntity<PageDto<TvSeriesDto>> actual =
                    restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageDto<TvSeriesDto>>() {});
            assertEquals(expectedInitialCommonSeries, actual.getBody());

            followRepository.save(follow);

            PageDto<TvSeriesDto> expected = PageDto.from(expectedSeriesDto);
            actual = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageDto<TvSeriesDto>>() {});
            assertEquals(expected, actual.getBody());
        }
    }
}
