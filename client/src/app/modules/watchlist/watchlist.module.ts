import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {WatchlistComponent} from './watchlist.component';
import {RouterModule} from "@angular/router";
import {routes} from "./watchlist.routing";

@NgModule({
  declarations: [
    WatchlistComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes)
  ]
})
export class WatchlistModule { }
