import {Component, Input, OnInit} from '@angular/core';
import {WatchlistStatus} from "../../../shared/models/watchlist-status";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {isSeries, TvSeries} from "../../../shared/models/tv-series";
import * as WatchlistTvSeriesState from '../../../root-store/watchlist/watchlist.state';
import * as WatchlistTvSeriesSelectors from '../../../root-store/watchlist/watchlist-series.selectors';
import * as WatchlistTvSeasonSelectors from '../../../root-store/watchlist/watchlist-seasons.selectors';
import * as WatchlistTvEpisodeSelectors from '../../../root-store/watchlist/watchlist-episodes.selectors';
import {isSeason, TvSeason} from "../../../shared/models/tv-season";
import {TvEpisode} from "../../../shared/models/tv-episode";

@Component({
  selector: 'app-watchlist-ribbon',
  templateUrl: './watchlist-ribbon.component.html',
  styleUrls: ['./watchlist-ribbon.component.less']
})
export class WatchlistRibbonComponent implements OnInit {
  @Input() entity: TvSeries | TvSeason | TvEpisode;
  status$: Observable<WatchlistStatus | undefined>;
  watchlistStatus = WatchlistStatus;

  constructor(private watchlistStore: Store<WatchlistTvSeriesState.State>) {}

  ngOnInit(): void {
    let watchlistStatusSelector;

    if (isSeries(this.entity)) {
      watchlistStatusSelector = WatchlistTvSeriesSelectors.selectWatchlistStatus(this.entity.id);
    } else if (isSeason(this.entity)) {
      watchlistStatusSelector = WatchlistTvSeasonSelectors.selectWatchlistStatus(this.entity.id);
    } else {
      watchlistStatusSelector = WatchlistTvEpisodeSelectors.selectWatchlistStatus(this.entity.id);
    }

    this.status$ = this.watchlistStore.select(watchlistStatusSelector);
  }
}
