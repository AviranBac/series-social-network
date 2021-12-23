import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationGuard} from "./core/auth/authentication.guard";
import {AppComponent} from "./app.component";

const routes: Routes = [
  {
    path: '',
    canActivate: [AuthenticationGuard],
    component: AppComponent // TODO: Change to HomeComponent later
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
