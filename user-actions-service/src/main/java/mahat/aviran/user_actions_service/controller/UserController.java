package mahat.aviran.user_actions_service.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.user_actions_service.entities.UpdateUserRequest;
import mahat.aviran.user_actions_service.services.UserService;
import mahat.aviran.user_actions_service.utils.exceptions.UnauthorizedActionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping()
    public ResponseEntity<Object> updateUserDetails(@Valid @RequestBody UpdateUserRequest updateUserRequest,
                                                    @AuthenticationPrincipal Jwt jwt) {
        String requestingUserName = jwt.getSubject();

        try {
            return ResponseEntity.ok(this.userService.updateUserDetails(updateUserRequest, requestingUserName));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Requesting User " + requestingUserName +
                    " or user " + updateUserRequest.getUserName() + " does not exist");
        } catch (UnauthorizedActionException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Requesting User " + requestingUserName +
                    " cannot edit user details for user " + updateUserRequest.getUserName());
        }
    }
}
