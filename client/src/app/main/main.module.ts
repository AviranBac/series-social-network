import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import * as WatchlistState from './root-store/watchlist/watchlist.state';
import * as WatchlistActions from './root-store/watchlist/watchlist.actions';
import {Store} from "@ngrx/store";
import {WatchlistFeatureStoreModule} from "./root-store/watchlist/watchlist-feature-store.module";
import {RouterModule} from "@angular/router";
import {routes} from "./main.routing";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    WatchlistFeatureStoreModule
  ]
})
export class MainModule {
  constructor(private watchlistStore: Store<WatchlistState.State>) {
    this.watchlistStore.dispatch(WatchlistActions.loadWatchlist());
  }
}
