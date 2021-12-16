package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.Collection;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
public class PageDto<T> {

    private Collection<T> content;
    private int totalPages;
    private long totalElements;

    public static <T> PageDto<T> from(Page<T> page) {
        return new PageDto<T>()
                .setContent(page.getContent())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(page.getTotalElements());
    }
}
