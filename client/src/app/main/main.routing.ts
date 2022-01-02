import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'watchlist',
    loadChildren: () => import('./modules/watchlist/watchlist.module').then(m => m.WatchlistModule)
  },
  {
    path: 'follow',
    loadChildren: () => import('./modules/follow/follow.module').then(m => m.FollowModule)
  },
  {

    path: 'series',
    loadChildren: () => import('./modules/series-details/series-details.module').then(m => m.SeriesDetailsModule)
  }
];
