package mahat.aviran.user_actions_service.entities;


import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class WatchlistRecordDetails {

    private WatchlistRecordDetails.Action action;
    private WatchlistRecordDetails.EntityType entityType;

    @NotBlank(message = "username can not be blank")
    private String username;

    @NotBlank(message = "entityId can not be blank")
    private String entityId;

    public enum Action {
        ADD,
        REMOVE;
    }

    public enum EntityType {
        SERIES,
        SEASON,
        EPISODE;
    }
}
