package mahat.aviran.tvseriesfetcher.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mahat.aviran.common.entities.persistence.PersistentGenre;
import mahat.aviran.common.entities.persistence.PersistentTvSeries;
import mahat.aviran.common.repositories.GenreRepository;
import mahat.aviran.common.repositories.TvSeriesRepository;
import mahat.aviran.common.repositories.UserRepository;
import org.springframework.stereotype.Service;
import rx.Observer;
import rx.schedulers.Schedulers;
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
    private final TvSeriesRepository tvSeriesRepository;

    @PostConstruct
    public void postConstruct() {
        genreSource
                .observeOn(Schedulers.newThread())
                .subscribe(getGenreObserver());
        seriesSource
                .observeOn(Schedulers.newThread())
                .subscribe(getSeriesObserver());
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
                tvSeriesRepository.save(tvSeries);
                log.info("Saved series to DB - id: " + tvSeries.getId() + ", name: " + tvSeries.getName());
            }
        };
    }
}
