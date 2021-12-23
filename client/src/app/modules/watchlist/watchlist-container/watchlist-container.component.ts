import {Component, OnInit} from '@angular/core';
import {WatchlistService} from "../../../core/services/watchlist.service";
import * as UserState from '../../../root-store/user/user.state';
import * as UserSelectors from '../../../root-store/user/user.selectors';
import {Store} from "@ngrx/store";
import {TvSeries} from "../../../shared/models/tv-series";
import {filter, map, Observable, switchMap} from "rxjs";
import {getStatusValue, SeriesStatus} from "../../../shared/models/series-status";

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist-container.component.html',
  styleUrls: ['./watchlist-container.component.less']
})
export class WatchlistContainerComponent implements OnInit {
  watchlist$: Observable<TvSeries[]>;
  seriesStatus = SeriesStatus;
  panelOpenState: boolean;

  constructor(private watchlistService: WatchlistService,
              private userStore: Store<UserState.State>) { }

  ngOnInit(): void {
    this.watchlist$ = this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap((username: string) => this.watchlistService.getWatchlistSeries(username))
    );
  }

  getStatusValue(status: string) {
    return getStatusValue(status);
  }
}
