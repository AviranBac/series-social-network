package mahat.aviran.data_api.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.data_api.dtos.PageDto;
import mahat.aviran.data_api.services.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    @ResponseBody
    public PageDto<UserDto> getUsersByFilter(@RequestParam int page,
                                             @RequestParam(required = false, defaultValue = "") String userName,
                                             @RequestParam(required = false, defaultValue = "") String firstName,
                                             @RequestParam(required = false, defaultValue = "") String lastName) {
        return userService.getUsersByFilter(page, userName, firstName, lastName);
    }

    @GetMapping("self")
    public ResponseEntity<Object> getUserDetails(@AuthenticationPrincipal Jwt jwt) {
        return this.getUserDetails(jwt.getSubject());
    }

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

    @GetMapping("{followingUsername}/following/{followedUsername}")
    public ResponseEntity<Object> getUserFollowing(@PathVariable String followingUsername, @PathVariable String followedUsername) {
        Optional<Boolean> isFollowing = userService.isFollowing(followingUsername, followedUsername);

        return isFollowing.
                <ResponseEntity<Object>> map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().body("Username " + followingUsername + " or username " + followedUsername + " does not exist"));
    }

    @GetMapping("followed")
    @ResponseBody
    public PageDto<UserDto> getFollowedUsers(@RequestParam int page, @RequestParam Sort.Direction sort) {
        return this.userService.getFollowedUsers(page, sort);
    }
}
