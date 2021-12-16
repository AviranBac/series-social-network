package mahat.aviran.user_actions_service.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.user_actions_service.entities.FollowDetails;
import mahat.aviran.user_actions_service.services.FollowService;
import mahat.aviran.user_actions_service.utils.exceptions.UsernameFromNotFoundException;
import mahat.aviran.user_actions_service.utils.exceptions.UsernameToNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowController {

    private final BindingResultHandler bindingResultHandler;
    private final FollowService followService;

    @PostMapping()
    public ResponseEntity<Object> updateFollow(@Valid @RequestBody FollowDetails followDetails, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.bindingResultHandler.generateErrorResponse(bindingResult);
        }

        try {
            switch (followDetails.getAction()) {
                case ADD:
                    return ResponseEntity.ok(this.followService.addFollow(followDetails.getUsernameFrom(), followDetails.getUsernameTo()));
                case REMOVE:
                    return ResponseEntity.ok(this.followService.removeFollow(followDetails.getUsernameFrom(), followDetails.getUsernameTo()));
                default:
                    return ResponseEntity.badRequest().body("Invalid action: " + followDetails.getAction());
            }
        } catch (UsernameFromNotFoundException e) {
            return ResponseEntity.badRequest().body("User " + followDetails.getUsernameFrom() + " does not exist");
        } catch (UsernameToNotFoundException e) {
            return ResponseEntity.badRequest().body("User " + followDetails.getUsernameTo() + " does not exist");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("User " + followDetails.getUsernameFrom() + " does not follow " + followDetails.getUsernameTo());
        } catch (EntityExistsException e) {
            return ResponseEntity.badRequest().body("User " + followDetails.getUsernameFrom() + " already follows " + followDetails.getUsernameTo());
        }
    }
}
