import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {
  loadWatchlist,
  loadWatchlistEpisodesSuccess,
  loadWatchlistSeasonsSuccess,
  loadWatchlistSeriesSuccess
} from "./watchlist.actions";
import * as UserSelectors from '../user/user.selectors';
import {filter, map, mergeMap, Observable, switchMap, withLatestFrom} from "rxjs";
import {WatchlistService} from "../../../core/services/watchlist.service";
import * as UserState from '../user/user.state';
import {Store} from "@ngrx/store";
import {WatchlistTvSeries} from "../../shared/models/tv-series";
import {normalize, schema} from "normalizr";
import {episodesFeatureKey, seasonsFeatureKey, seriesFeatureKey, State} from "./watchlist.state";

@Injectable()
export class WatchlistEffects {

  constructor(
    private actions$: Actions,
    private watchlistService: WatchlistService,
    private userStore: Store<UserState.State>
  ) {}

  loadWatchlist$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadWatchlist),
      withLatestFrom(this.selectUsername()),
      switchMap(([action, username]) => this.watchlistService.getWatchlistSeries(username).pipe(
        map((allSeries: WatchlistTvSeries[]) => this.normalizeWatchlist(allSeries)),
        map((normalizedWatchlist: State) => [
          loadWatchlistSeriesSuccess({ normalizedSeries: normalizedWatchlist[seriesFeatureKey]}),
          loadWatchlistSeasonsSuccess({ normalizedSeasons: normalizedWatchlist[seasonsFeatureKey]}),
          loadWatchlistEpisodesSuccess({ normalizedEpisodes: normalizedWatchlist[episodesFeatureKey]}),
        ]),
        mergeMap(actions => actions)
      ))
    )
  );

  private normalizeWatchlist(seriesBeforeNormalization: WatchlistTvSeries[]): State {
    const episodes = new schema.Entity(
      'episodes',
      {},
      { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'episode'})) }
    );
    const seasons = new schema.Entity(
      'seasons',
      { episodes: [episodes] },
      { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'season'})) }
    );
    const series = new schema.Entity(
      'series',
      { seasons: [seasons] },
      { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'series'})) }
    );

    return normalize(seriesBeforeNormalization, [series]).entities as unknown as State;
  }

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
    )
  }
}
