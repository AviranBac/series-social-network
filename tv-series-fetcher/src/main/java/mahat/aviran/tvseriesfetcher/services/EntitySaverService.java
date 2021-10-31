package mahat.aviran.tvseriesfetcher.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.tvseriesfetcher.entities.persistance.PersistentGenre;
import mahat.aviran.tvseriesfetcher.entities.persistance.PersistentTvSeries;
import mahat.aviran.tvseriesfetcher.repositories.GenreRepository;
import org.springframework.stereotype.Service;
import rx.Observer;
import rx.subjects.PublishSubject;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Getter @RequiredArgsConstructor
@Log4j2
public class EntitySaverService {

    private final PublishSubject<List<PersistentGenre>> genreSource = PublishSubject.create();
    private final PublishSubject<PersistentTvSeries> seriesSource = PublishSubject.create();
    private final GenreRepository genreRepository;

    @PostConstruct
    public void postConstruct() {
        genreSource.subscribe(getGenreObserver());
        seriesSource.subscribe(getSeriesObserver());
    }

    private Observer<List<PersistentGenre>> getGenreObserver() {
        return new Observer<>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onNext(List<PersistentGenre> genres) {
                genreRepository.saveAll(genres);
                log.info("Saved all genres to DB");
            }
        };
    }

    private Observer<PersistentTvSeries> getSeriesObserver() {
        return new Observer<>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable throwable) {}

            @Override
            public void onNext(PersistentTvSeries tvSeries) {
                log.info("onNext");
            }
        };
    }
}
