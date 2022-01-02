import {Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {loadSeasons, loadSeasonsSuccess, loadSeries, loadSeriesSuccess} from "./watchlist.actions";
import * as UserSelectors from '../user/user.selectors';
import {filter, map, Observable, switchMap, withLatestFrom} from "rxjs";
import {WatchlistService} from "../../../core/services/watchlist.service";
import * as UserState from '../user/user.state';
import {Store} from "@ngrx/store";
import {TvSeries} from "../../shared/models/tv-series";
import {TvSeason} from "../../shared/models/tv-season";

@Injectable()
export class WatchlistEffects {

  constructor(
    private actions$: Actions,
    private watchlistService: WatchlistService,
    private userStore: Store<UserState.State>
  ) {}

  loadSeries$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadSeries),
      withLatestFrom(this.selectUsername()),
      switchMap(([action, username]) => this.watchlistService.getWatchlistSeries(username).pipe(
        map((series: TvSeries[]) => loadSeriesSuccess({series}))
      ))
    )
  );

  loadSeasons$ = createEffect(() =>
    this.actions$.pipe(
      ofType(loadSeasons),
      withLatestFrom(this.selectUsername()),
      switchMap(([action, username]) => this.watchlistService.getWatchlistSeriesDetails(username, action.seriesId).pipe(
        map((seasons: TvSeason[]) => loadSeasonsSuccess({seasons}))
      ))
    )
  );

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
    )
  }
}
