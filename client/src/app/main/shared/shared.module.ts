import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {RouterModule} from "@angular/router";
import {PaginationTableComponent} from './components/pagination-table/pagination-table.component';
import {MatTableModule} from "@angular/material/table";
import {NgxPaginationModule} from "ngx-pagination";
import {MaterialElevationDirective} from "./directives/material-elevation.directive";
import {WatchlistRibbonComponent} from "./components/watchlist-ribbon/watchlist-ribbon.component";
import {WatchlistFeatureStoreModule} from "../root-store/watchlist/watchlist-feature-store.module";

@NgModule({
  declarations: [
    NavBarComponent,
    PaginationTableComponent,
    MaterialElevationDirective,
    WatchlistRibbonComponent
  ],
  exports: [
    NavBarComponent,
    PaginationTableComponent,
    WatchlistRibbonComponent,
    MaterialElevationDirective
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    RouterModule,
    MatTableModule,
    NgxPaginationModule,
    WatchlistFeatureStoreModule
  ]
})
export class SharedModule { }
