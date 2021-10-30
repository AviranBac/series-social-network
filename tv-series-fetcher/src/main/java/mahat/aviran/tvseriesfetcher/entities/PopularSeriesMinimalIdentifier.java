package mahat.aviran.tvseriesfetcher.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopularSeriesMinimalIdentifier {

    private int id;
    @JsonProperty(value = "original_language")
    private String originalLanguage;
}
