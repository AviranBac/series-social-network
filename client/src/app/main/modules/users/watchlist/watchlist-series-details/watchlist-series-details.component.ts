import {Component, Input, OnInit} from '@angular/core';
import {TvSeason} from "../../../../shared/models/tv-season";
import {Observable} from 'rxjs';
import {TvEpisode} from "../../../../shared/models/tv-episode";

@Component({
  selector: 'app-watchlist-series-details',
  templateUrl: './watchlist-series-details.component.html',
  styleUrls: ['./watchlist-series-details.component.less']
})
export class WatchlistSeriesDetailsComponent implements OnInit {
  @Input() seasonsProvider: () => Observable<TvSeason[]>;
  @Input() disableRibbonClick: boolean;

  tvSeasons$: Observable<TvSeason[]>;

  constructor() { }

  ngOnInit(): void {
    this.tvSeasons$ = this.seasonsProvider();
  }

  trackBySeasonId(index: number, season: TvSeason): number {
    return Number(season.id);
  }

  trackByEpisodeId(index: number, episode: TvEpisode): number {
    return Number(episode.id);
  }
}
