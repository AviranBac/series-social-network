import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {
  loadWatchlist,
  upsertWatchlistEpisodesSuccess,
  upsertWatchlistSeasonsSuccess,
  upsertWatchlistSeriesSuccess,
  upsertWatchlistSuccess
} from "./watchlist.actions";
import * as UserSelectors from '../user/user.selectors';
import {filter, map, mergeMap, Observable, switchMap, withLatestFrom} from "rxjs";
import {WatchlistService} from "../../../core/services/watchlist.service";
import * as UserState from '../user/user.state';
import {Store} from "@ngrx/store";
import {WatchlistTvSeries} from "../../shared/models/tv-series";
import {episodesFeatureKey, seasonsFeatureKey, seriesFeatureKey, State} from "./watchlist.state";
import {normalizeWatchlistSeries} from "../../shared/normalizers/watchlist-normalizer";

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
        map((series: WatchlistTvSeries[]) => upsertWatchlistSuccess({ series }))
      ))
    )
  );

  upsertWatchlistSuccess$ = createEffect(() =>
    this.actions$.pipe(
      ofType(upsertWatchlistSuccess),
      map(({series}) => normalizeWatchlistSeries(series)),
      map((normalizedWatchlist: State) => [
        upsertWatchlistSeriesSuccess({ normalizedSeries: normalizedWatchlist[seriesFeatureKey]}),
        upsertWatchlistSeasonsSuccess({ normalizedSeasons: normalizedWatchlist[seasonsFeatureKey]}),
        upsertWatchlistEpisodesSuccess({ normalizedEpisodes: normalizedWatchlist[episodesFeatureKey]})
      ]),
      mergeMap(actions => actions)
    )
  );

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
    )
  }
}
