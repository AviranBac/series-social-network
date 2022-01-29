import {Routes} from "@angular/router";

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./modules/home/home.module').then(m => m.HomeModule)
  },
  {
    path: 'users',
    loadChildren: () => import('./modules/users/users.module').then(m => m.UsersModule)
  },
  {
    path: 'series',
    loadChildren: () => import('./modules/series/series.module').then(m => m.SeriesModule)
  },
  {
    path: 'statistics',
    loadChildren: () => import('./modules/statistics/statistics.module').then(m => m.StatisticsModule)
  }
];
