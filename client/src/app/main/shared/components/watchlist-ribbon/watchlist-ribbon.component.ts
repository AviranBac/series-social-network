import {Component, Input, OnChanges} from '@angular/core';
import {WatchlistStatus} from "../../models/watchlist-status";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";
import {isSeries, TvSeries} from "../../models/tv-series";
import * as WatchlistTvSeriesState from '../../../root-store/watchlist/watchlist.state';
import * as WatchlistTvSeriesSelectors from '../../../root-store/watchlist/watchlist-series.selectors';
import * as WatchlistTvSeasonSelectors from '../../../root-store/watchlist/watchlist-seasons.selectors';
import * as WatchlistTvEpisodeSelectors from '../../../root-store/watchlist/watchlist-episodes.selectors';
import {isSeason, TvSeason} from "../../models/tv-season";
import {TvEpisode} from "../../models/tv-episode";
import {EntityType, WatchlistService} from "../../../../core/services/watchlist.service";

@Component({
  selector: 'app-watchlist-ribbon',
  templateUrl: './watchlist-ribbon.component.html',
  styleUrls: ['./watchlist-ribbon.component.less']
})
export class WatchlistRibbonComponent implements OnChanges {
  @Input() entity: TvSeries | TvSeason | TvEpisode;
  @Input() disableClick: boolean = false;

  status$: Observable<WatchlistStatus | undefined>;
  watchlistStatus = WatchlistStatus;

  constructor(private watchlistStore: Store<WatchlistTvSeriesState.State>,
              private watchlistService: WatchlistService) {}

  ngOnChanges(): void {
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

  updateWatchlist($event: any, currentWatchlistStatus: WatchlistStatus): void {
    // Disabling parent link
    $event.stopPropagation();
    $event.preventDefault();

    if (!this.disableClick) {
      let entityType: EntityType;

      if (isSeries(this.entity)) {
        entityType = EntityType.SERIES;
      } else if (isSeason(this.entity)) {
        entityType = EntityType.SEASON;
      } else {
        entityType = EntityType.EPISODE;
      }

      this.watchlistService.updateWatchlist(currentWatchlistStatus, entityType, this.entity.id).subscribe();
    }
  }
}
