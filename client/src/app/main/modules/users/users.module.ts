import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {routes} from "./users.routing";
import {NgxPaginationModule} from 'ngx-pagination';
import {MatTableModule} from "@angular/material/table";
import {SharedModule} from "../../shared/shared.module";
import {UserDetailsComponent} from './user-details/user-details.component';
import {MatTabsModule} from "@angular/material/tabs";
import {UserFollowersComponent} from "./user-followers/user-followers.component";
import {UserFollowingComponent} from "./user-following/user-following.component";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatListModule} from "@angular/material/list";
import {WatchlistContainerComponent} from "./watchlist/watchlist-container/watchlist-container.component";
import {WatchlistSeriesDetailsComponent} from "./watchlist/watchlist-series-details/watchlist-series-details.component";
import { SearchUsersComponent } from './search-users/search-users.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    UserFollowersComponent,
    UserFollowingComponent,
    UserDetailsComponent,
    WatchlistContainerComponent,
    WatchlistSeriesDetailsComponent,
    SearchUsersComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    MatTableModule,
    MatTabsModule,
    MatExpansionModule,
    MatListModule,
    ReactiveFormsModule,
    MatButtonModule
  ]
})
export class UsersModule { }
