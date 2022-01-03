import {Component, Input, OnInit} from '@angular/core';
import {TvSeason} from "../../../shared/models/tv-season";
import {Observable} from 'rxjs';
import * as WatchlistSeasonSelectors from "../../../root-store/watchlist/watchlist-seasons.selectors";
import * as WatchlistState from "../../../root-store/watchlist/watchlist.state";
import {WatchlistTvSeriesEntity} from "../../../root-store/watchlist/watchlist.state";
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-watchlist-series-details',
  templateUrl: './watchlist-series-details.component.html',
  styleUrls: ['./watchlist-series-details.component.less']
})
export class WatchlistSeriesDetailsComponent implements OnInit {
  @Input() series: WatchlistTvSeriesEntity;
  tvSeasons$: Observable<TvSeason[]>;

  constructor(private watchlistStore: Store<WatchlistState.State>) { }

  ngOnInit(): void {
    this.tvSeasons$ = this.watchlistStore.select(WatchlistSeasonSelectors.selectSeasonsBySeries(this.series.id));
  }
}
