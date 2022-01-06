import {Component, Input, OnInit} from '@angular/core';
import * as WatchlistState from '../../../../root-store/watchlist/watchlist.state';
import * as WatchlistSelectors from '../../../../root-store/watchlist/watchlist-series.selectors';
import * as WatchlistSeasonsSelectors from '../../../../root-store/watchlist/watchlist-seasons.selectors';
import {Store} from "@ngrx/store";
import {filter, iif, map, Observable, shareReplay, switchMap} from "rxjs";
import {getStatusValue, SeriesStatus} from "../../../../shared/models/series-status";
import {WatchlistTvSeries} from "../../../../shared/models/tv-series";
import {WatchlistService} from "../../../../../core/services/watchlist.service";
import {RouterState} from "@ngrx/router-store";
import {
  extractSeasonsAndEpisodes,
  NormalizedWatchlist,
  normalizeWatchlistSeries,
  sortSeries,
  WatchlistTvSeriesEntity
} from "../../../../shared/normalizers/watchlist-normalizer";
import {WatchlistTvSeason} from "../../../../shared/models/tv-season";

@Component({
  selector: 'app-watchlist',
  templateUrl: './watchlist-container.component.html',
  styleUrls: ['./watchlist-container.component.less']
})
export class WatchlistContainerComponent implements OnInit {
  @Input() username$: Observable<string>;
  @Input() isCurrentUser$: Observable<boolean>;

  watchlistSeries$: Observable<WatchlistTvSeriesEntity[]>;
  seriesStatus = SeriesStatus;
  panelOpenState: boolean;

  private watchlist$: Observable<NormalizedWatchlist>;

  constructor(private watchlistStore: Store<WatchlistState.State>,
              private routerStore: Store<RouterState>,
              private watchlistService: WatchlistService) { }

  ngOnInit(): void {
    this.watchlistSeries$ = this.isCurrentUser$.pipe(
      switchMap(isCurrentUser => iif(
        () => isCurrentUser,
        this.watchlistStore.select(WatchlistSelectors.selectAllSeries),
        this.watchlist$.pipe(
          map((normalizedWatchlist: NormalizedWatchlist) => sortSeries(normalizedWatchlist.series))
        )
      ))
    );

    this.watchlist$ = this.isCurrentUser$.pipe(
      filter(isCurrentUser => !isCurrentUser),
      switchMap(() => this.username$.pipe(
        switchMap(username => this.watchlistService.getWatchlistSeries(username).pipe(
          map((series: WatchlistTvSeries[]) => normalizeWatchlistSeries(series)),
          shareReplay()
        ))
      ))
    );
  }

  getStatusValue(status: string) {
    return getStatusValue(status);
  }

  trackBySeriesId(index: number, series: WatchlistTvSeriesEntity): number {
    return Number(series.id);
  }

  getSeasonsProvider(clickedSeries: WatchlistTvSeriesEntity): () => Observable<WatchlistTvSeason[]> {
    return () => this.isCurrentUser$.pipe(
      switchMap(isCurrentUser => iif(
        () => isCurrentUser,
        this.watchlistStore.select(WatchlistSeasonsSelectors.selectSeasonsBySeries(clickedSeries.id)),
        this.watchlist$.pipe(
          map(normalizedWatchlist => extractSeasonsAndEpisodes(clickedSeries, normalizedWatchlist.seasons, normalizedWatchlist.episodes))
        )
      ))
    );
  }
}
