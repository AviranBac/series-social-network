package mahat.aviran.user_actions_service.entities;


import lombok.*;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class WatchlistRecordDetails {

    private WatchlistRecordDetails.Action action;
    private WatchlistRecordDetails.EntityType entityType;
    private String username;
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
