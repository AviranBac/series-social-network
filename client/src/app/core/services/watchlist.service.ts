import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WatchlistTvSeries} from "../../main/shared/models/tv-series";
import {filter, map, Observable, switchMap, take, tap} from "rxjs";
import {WatchlistStatus} from "../../main/shared/models/watchlist-status";
import {Store} from "@ngrx/store";
import * as WatchlistState from "../../main/root-store/watchlist/watchlist.state";
import * as UserState from "../../main/root-store/user/user.state";
import * as UserSelectors from "../../main/root-store/user/user.selectors";
import * as WatchlistActions from "../../main/root-store/watchlist/watchlist.actions";

export enum EntityType {
  SERIES = 'SERIES',
  SEASON = 'SEASON',
  EPISODE = 'EPISODE'
}

enum ActionType {
  ADD = 'ADD',
  REMOVE = 'REMOVE'
}

interface WatchlistRecordDetails {
  action: ActionType,
  entityType: EntityType,
  username: string,
  entityId: string
}

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor(private http: HttpClient,
              private watchlistStore: Store<WatchlistState.State>,
              private userStore: Store<UserState.State>) { }

  loadWatchlistSeries(username: string): Observable<WatchlistTvSeries[]> {
    return this.http.get<WatchlistTvSeries[]>(`${environment.apiGatewayUrl}/data/watchlist/${username}`);
  }

  updateWatchlist(currentWatchlistStatus: WatchlistStatus, entityType: EntityType, entityId: string): Observable<WatchlistTvSeries[]> {
    const actionType = currentWatchlistStatus === WatchlistStatus.COMPLETE ? ActionType.REMOVE : ActionType.ADD;

    return this.selectUsername().pipe(
      map(username => ({
        action: actionType,
        entityType,
        username,
        entityId
      })),
      switchMap((requestBody: WatchlistRecordDetails) => this.http.post<WatchlistTvSeries[]>(`${environment.apiGatewayUrl}/user-actions/watchlist`, requestBody).pipe(
        tap((series: WatchlistTvSeries[]) => {
          this.watchlistStore.dispatch(WatchlistActions.upsertWatchlistSuccess({ series }));
        })
      ))
    );
  }

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      take(1)
    )
  }
}
