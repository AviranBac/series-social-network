package mahat.aviran.user_actions_service.api;

import mahat.aviran.common.entities.persistence.PersistentFollow;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.FollowRepository;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.user_actions_service.dtos.FollowDto;
import mahat.aviran.user_actions_service.entities.FollowDetails;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "test")
public class FollowControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowRepository followRepository;

    private PersistentUser user1InDB = new PersistentUser()
            .setUserName("Test_AviranB")
            .setFirstName("Avi")
            .setLastName("Bac");

    private PersistentUser user2InDB = new PersistentUser()
            .setUserName("Test_AviranB2")
            .setFirstName("Avi")
            .setLastName("Bac");

    @BeforeAll
    public void initializeUsers() {
        userRepository.saveAll(List.of(user1InDB, user2InDB));
    }

    @AfterAll
    public void removeUsers() {
        followRepository.removeFollow(user1InDB.getUserName(), user2InDB.getUserName());
        userRepository.deleteAllInBatch(List.of(user1InDB, user2InDB));
    }

    @Test
    public void updateFollow_expectUpdate() {
        String url = "http://localhost:" + port + "/follows";
        FollowDetails requestBody = new FollowDetails()
                .setAction(FollowDetails.Action.ADD)
                .setUsernameFrom(user1InDB.getUserName())
                .setUsernameTo(user2InDB.getUserName());

        FollowDto expected = new FollowDto()
                .setFrom(user1InDB.getUserName())
                .setTo(user2InDB.getUserName());
        FollowDto actual = restTemplate.postForObject(url, requestBody, FollowDto.class);
        assertEquals(expected, actual);

        PersistentFollow actualFollow = followRepository.findFollow(user1InDB.getUserName(), user2InDB.getUserName());
        assertEquals(user1InDB, actualFollow.getUsernameFrom());
        assertEquals(user2InDB, actualFollow.getUsernameTo());

        requestBody = new FollowDetails()
                .setAction(FollowDetails.Action.REMOVE)
                .setUsernameFrom(user1InDB.getUserName())
                .setUsernameTo(user2InDB.getUserName());
        actual = restTemplate.postForObject(url, requestBody, FollowDto.class);
        assertEquals(expected, actual);
        assertNull(followRepository.findFollow(user1InDB.getUserName(), user2InDB.getUserName()));
    }
}
