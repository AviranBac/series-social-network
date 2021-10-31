package mahat.aviran.tvseriesfetcher.entities.raw_request_entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Genre {

    private int id;
    private String name;
}
