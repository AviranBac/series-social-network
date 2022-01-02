import {Component} from '@angular/core';
import {WatchlistStatus} from "../../../shared/models/watchlist-status";
import {Store} from "@ngrx/store";
import {Observable} from "rxjs";

@Component({
  selector: 'app-watchlist-ribbon',
  templateUrl: './watchlist-ribbon.component.html',
  styleUrls: ['./watchlist-ribbon.component.less']
})
export class WatchlistRibbonComponent {
  status$: Observable<WatchlistStatus>;
  watchlistStatus = WatchlistStatus;

  constructor(private watchlistStore: Store<any>) {
    // TODO: logic for watchlist status
  }
}
