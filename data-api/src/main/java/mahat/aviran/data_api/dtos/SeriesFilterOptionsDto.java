package mahat.aviran.data_api.dtos;

import lombok.*;
import lombok.experimental.Accessors;
import mahat.aviran.common.entities.SeriesStatus;
import mahat.aviran.common.entities.persistence.PersistentGenre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Accessors(chain = true)
@Getter @Setter @ToString @EqualsAndHashCode @AllArgsConstructor
public class SeriesFilterOptionsDto {

    private Set<SeriesStatus> seriesStatuses;
    private Set<GenreDto> genres;

    public SeriesFilterOptionsDto(List<PersistentGenre> genres) {
        this.seriesStatuses = Set.of(SeriesStatus.values());
        this.genres = genres.stream().map(GenreDto::from).collect(Collectors.toSet());
    }
}
