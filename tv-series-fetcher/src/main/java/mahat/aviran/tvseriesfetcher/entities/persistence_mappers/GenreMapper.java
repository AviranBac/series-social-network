package mahat.aviran.tvseriesfetcher.entities.persistence_mappers;

import mahat.aviran.common.entities.persistence.PersistentGenre;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.Genre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public PersistentGenre toPersistent(Genre genre) {
        return new PersistentGenre(genre.getId(), genre.getName());
    }
}
