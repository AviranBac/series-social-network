import {Component, Input, OnInit} from '@angular/core';
import {TvSeries} from "../../../shared/models/tv-series";
import {TvSeason} from "../../../shared/models/tv-season";
import {filter, map, Observable, switchMap} from 'rxjs';
import {WatchlistService} from "../../../core/services/watchlist.service";
import * as UserSelectors from "../../../root-store/user/user.selectors";
import * as UserState from "../../../root-store/user/user.state";
import {Store} from '@ngrx/store';

@Component({
  selector: 'app-watchlist-series-details',
  templateUrl: './watchlist-series-details.component.html',
  styleUrls: ['./watchlist-series-details.component.less']
})
export class WatchlistSeriesDetailsComponent implements OnInit {
  @Input() series: TvSeries;
  tvSeasons$: Observable<TvSeason[]>;

  constructor(private watchlistService: WatchlistService,
              private userStore: Store<UserState.State>) { }

  ngOnInit(): void {
    this.tvSeasons$ = this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap((username: string) => this.watchlistService.getWatchlistSeriesDetails(username, this.series.id))
    );
  }
}
