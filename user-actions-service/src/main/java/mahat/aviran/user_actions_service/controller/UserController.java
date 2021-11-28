package mahat.aviran.user_actions_service.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.user_actions_service.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "{userName}")
    public ResponseEntity<Object> getUser(@PathVariable String userName) {
        Optional<UserDto> optionalUser = this.userService.findUser(userName);
        return optionalUser.isPresent() ?
                ResponseEntity.ok(optionalUser) :
                ResponseEntity.badRequest().body("Username " + userName + " does not exist");
    }
}
