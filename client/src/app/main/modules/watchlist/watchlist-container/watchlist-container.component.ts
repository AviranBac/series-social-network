import {Component, OnInit} from '@angular/core';
import * as WatchlistState from '../../../root-store/watchlist/watchlist.state';
import {WatchlistTvSeriesEntity} from '../../../root-store/watchlist/watchlist.state';
import * as WatchlistSelectors from '../../../root-store/watchlist/watchlist-series.selectors';
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {getStatusValue, SeriesStatus} from "../../../shared/models/series-status";

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist-container.component.html',
  styleUrls: ['./watchlist-container.component.less']
})
export class WatchlistContainerComponent implements OnInit {
  watchlist$: Observable<WatchlistTvSeriesEntity[]>;
  seriesStatus = SeriesStatus;
  panelOpenState: boolean;

  constructor(private watchlistStore: Store<WatchlistState.State>) { }

  ngOnInit(): void {
    this.watchlist$ = this.watchlistStore.select(WatchlistSelectors.selectAllSeries);
  }

  getStatusValue(status: string) {
    return getStatusValue(status);
  }
}
