package mahat.aviran.user_actions_service.controller;

import lombok.RequiredArgsConstructor;
import mahat.aviran.user_actions_service.entities.WatchlistRecordDetails;
import mahat.aviran.user_actions_service.services.WatchlistService;
import mahat.aviran.user_actions_service.utils.exceptions.WatchlistRecordExistsException;
import mahat.aviran.user_actions_service.utils.exceptions.WatchlistRecordNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.InputMismatchException;

@Controller
@RequestMapping("/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final BindingResultHandler bindingResultHandler;
    private final WatchlistService watchlistService;

    @PostMapping()
    public ResponseEntity<Object> updateWatchlist(@Valid @RequestBody WatchlistRecordDetails watchlistRecordDetails,
                                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.bindingResultHandler.generateErrorResponse(bindingResult);
        }

        try {
            switch (watchlistRecordDetails.getAction()) {
                case ADD:
                    return ResponseEntity.ok(this.watchlistService.addToWatchlist(watchlistRecordDetails.getUsername(), watchlistRecordDetails.getEntityType(), watchlistRecordDetails.getEntityId()));
                case REMOVE:
                    return ResponseEntity.ok(this.watchlistService.removeFromWatchlist(watchlistRecordDetails.getUsername(), watchlistRecordDetails.getEntityType(), watchlistRecordDetails.getEntityId()));
                default:
                    return ResponseEntity.badRequest().body("Invalid action: " + watchlistRecordDetails.getAction());
            }
        } catch (InputMismatchException e) {
            return ResponseEntity.badRequest().body("Invalid input: " + watchlistRecordDetails.getEntityType());
        } catch (WatchlistRecordExistsException e) {
            return ResponseEntity.badRequest().body("User " + watchlistRecordDetails.getUsername() + " already has in watchlist");
        } catch (WatchlistRecordNotFoundException e) {
            return ResponseEntity.badRequest().body("User " + watchlistRecordDetails.getUsername() + " doesn't have it in watchlist");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("User " + watchlistRecordDetails.getUsername() + " or " +
                    watchlistRecordDetails.getEntityType() + " entity id " +
                    watchlistRecordDetails.getEntityId() + " does not exist");
        }
    }
}
