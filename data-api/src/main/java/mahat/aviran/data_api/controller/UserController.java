package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.data_api.dtos.PageDto;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.data_api.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<Object> getUserDetails(@PathVariable String username) {
        Optional<UserDto> userDto = userService.getUserDetails(username);

        return userDto.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Username " + username + " does not exist"));
    }

    @GetMapping("{username}/followers")
    public ResponseEntity<Object> getUserFollowers(@PathVariable String username, @RequestParam int page) {
        Optional<PageDto<UserDto>> followers = userService.getUserFollowers(username, page);

        return followers.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Username " + username + " does not exist"));
    }

    @GetMapping("{username}/following")
    public ResponseEntity<Object> getUserFollowing(@PathVariable String username, @RequestParam int page) {
        Optional<PageDto<UserDto>> followers = userService.getUserFollowing(username, page);

        return followers.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Username " + username + " does not exist"));
    }
}