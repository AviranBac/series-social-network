import {Component, Input, OnInit} from '@angular/core';
import {TvSeries} from "../../../shared/models/tv-series";
import {TvSeason} from "../../../shared/models/tv-season";
import {Observable} from 'rxjs';
import * as WatchlistSeasonSelectors from "../../../root-store/watchlist/watchlist-seasons.selectors";
import * as WatchlistActions from "../../../root-store/watchlist/watchlist.actions";
import * as WatchlistState from "../../../root-store/watchlist/watchlist.state";
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-watchlist-series-details',
  templateUrl: './watchlist-series-details.component.html',
  styleUrls: ['./watchlist-series-details.component.less']
})
export class WatchlistSeriesDetailsComponent implements OnInit {
  @Input() series: TvSeries;
  tvSeasons$: Observable<TvSeason[]>;

  constructor(private watchlistStore: Store<WatchlistState.State>) { }

  ngOnInit(): void {
    this.watchlistStore.dispatch(WatchlistActions.loadSeasons({ seriesId: this.series.id }));
    this.tvSeasons$ = this.watchlistStore.select(WatchlistSeasonSelectors.selectSeasons);
  }
}
