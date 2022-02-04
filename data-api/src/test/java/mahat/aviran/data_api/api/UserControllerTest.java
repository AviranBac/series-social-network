package mahat.aviran.data_api.api;

import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.common.repositories.UserRepository;
import mahat.aviran.data_api.dtos.PageDto;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "test")
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    private PersistentUser userInDB = new PersistentUser()
            .setUserName("Test_AviranB")
            .setFirstName("Avi")
            .setLastName("Bac");

    @BeforeAll
    public void addUser() {
        userRepository.save(userInDB);
    }

    @AfterAll
    public void removeUser() {
        userRepository.delete(userInDB);
    }

    @Test
    public void userInDatabase_expectUserDetailsFound() {
        UserDto expected = new UserDto()
                .setUserName(userInDB.getUserName())
                .setFirstName(userInDB.getFirstName())
                .setLastName(userInDB.getLastName());

        String url = "http://localhost:" + port + "/users/" + userInDB.getUserName();
        UserDto actual = this.restTemplate.getForObject(url, UserDto.class);
        assertEquals(expected, actual);
    }

    @Test
    public void userInDatabase_expectUserFoundInSearch() {
        UserDto expectedUserDto = new UserDto()
                .setUserName(userInDB.getUserName())
                .setFirstName(userInDB.getFirstName())
                .setLastName(userInDB.getLastName());
        PageDto<UserDto> expectedResult = PageDto.from(expectedUserDto);

        String url = "http://localhost:" + port + "/users?page=0&userName=Test_Avi";
        ResponseEntity<PageDto<UserDto>> actual =
                this.restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<PageDto<UserDto>>() {});
        assertEquals(expectedResult, actual.getBody());
    }
}
