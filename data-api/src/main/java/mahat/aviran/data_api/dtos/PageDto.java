package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Collection;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PageDto<T> {

    private Collection<T> content;
    private int totalPages;
    private long totalElements;
}
