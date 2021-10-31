package mahat.aviran.tvseriesfetcher.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.mappers.GenreMapper;
import mahat.aviran.tvseriesfetcher.entities.persistance.PersistentGenre;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.Genre;
import mahat.aviran.tvseriesfetcher.entities.raw_request_entities.TvSeriesFullEntity;
import org.springframework.stereotype.Service;
import rx.Observer;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class MapperService {

    private final PublishSubject<List<Genre>> genreSource = PublishSubject.create();
    private final PublishSubject<TvSeriesFullEntity> seriesSource = PublishSubject.create();
    private final GenreMapper genreMapper;
    private final EntitySaverService entitySaverService;

    @PostConstruct
    public void postConstruct() {
        genreSource.subscribe(getGenreObserver());
        seriesSource.subscribe(getSeriesObserver());
    }

    private Observer<List<Genre>> getGenreObserver() {
        return new Observer<>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onNext(List<Genre> genres) {
                List<PersistentGenre> persistentGenres = genres.stream()
                        .map(genreMapper::toPersistent)
                        .collect(Collectors.toList());
                entitySaverService.getGenreSource().onNext(persistentGenres);
            }
        };
    }

    private Observer<TvSeriesFullEntity> getSeriesObserver() {
        return new Observer<>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onNext(TvSeriesFullEntity tvSeriesFullEntity) {
                log.info("onNext");
            }
        };
    }
}
