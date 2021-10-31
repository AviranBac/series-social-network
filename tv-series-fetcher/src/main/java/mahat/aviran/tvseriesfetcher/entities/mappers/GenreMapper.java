package mahat.aviran.tvseriesfetcher.entities.mappers;

import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.Genre;
import mahat.aviran.tvseriesfetcher.entities.persistance.PersistentGenre;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public PersistentGenre toPersistent(Genre genre) {
        return new PersistentGenre(genre.getId(), genre.getName());
    }
}
