package mahat.aviran.user_actions_service.controller;

import lombok.*;
import mahat.aviran.common.entities.dtos.UserDto;
import mahat.aviran.common.entities.persistence.PersistentUser;
import mahat.aviran.user_actions_service.entities.UserCredentials;
import mahat.aviran.user_actions_service.entities.UserRegistrationDetails;
import mahat.aviran.user_actions_service.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BindingResultHandler bindingResultHandler;

    @GetMapping(value = "login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody UserCredentials credentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.bindingResultHandler.generateErrorResponse(bindingResult);
        }

        return this.userService.isUserAuthenticated(credentials) ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "{userName}")
    public ResponseEntity<Object> getUser(@PathVariable String userName) {
        Optional<UserDto> optionalUser = this.userService.findUser(userName);
        return optionalUser.isPresent() ?
                ResponseEntity.ok(optionalUser) :
                ResponseEntity.badRequest().body("Username " + userName + " does not exist");
    }

    @PostMapping(value = "register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserRegistrationDetails userRegistrationDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.bindingResultHandler.generateErrorResponse(bindingResult);
        }

        try {
            return ResponseEntity.ok(this.userService.saveUser(userRegistrationDetails));
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body("User " + userRegistrationDetails.getUserName() + " already exists");
        }
    }
}
