package mahat.aviran.tvseriesfetcher.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResult<T> {

    private int page;
    private List<T> results;

    @JsonProperty(value = "total_pages")
    private int totalPages;
    @JsonProperty(value = "total_results")
    private int totalResults;
}
