import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WatchlistContainerComponent} from './watchlist-container/watchlist-container.component';
import {RouterModule} from "@angular/router";
import {routes} from "./watchlist.routing";
import {MatExpansionModule} from '@angular/material/expansion';
import {WatchlistSeriesDetailsComponent} from './watchlist-series-details/watchlist-series-details.component';
import {MatListModule} from '@angular/material/list';

@NgModule({
  declarations: [
    WatchlistContainerComponent,
    WatchlistSeriesDetailsComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatExpansionModule,
    MatListModule
  ]
})
export class WatchlistModule { }
