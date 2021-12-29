import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationGuard} from "./core/auth/authentication.guard";

const routes: Routes = [
  {
    path: '',
    canLoad: [AuthenticationGuard],
    loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'watchlist',
    canLoad: [AuthenticationGuard],
    loadChildren: () => import('./modules/watchlist/watchlist.module').then(m => m.WatchlistModule)
  },
  {
    path: 'follow',
    canLoad: [AuthenticationGuard],
    loadChildren: () => import('./modules/follow/follow.module').then(m => m.FollowModule)
  },
  {

    path: 'series',
    canLoad: [AuthenticationGuard],
    loadChildren: () => import('./modules/series-details/series-details.module').then(m => m.SeriesDetailsModule)
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full'
  }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
