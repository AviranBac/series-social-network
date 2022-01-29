import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {AuthenticationGuard} from "./core/auth/authentication.guard";

const routes: Routes = [
  {
    path: '',
    canLoad: [AuthenticationGuard],
    loadChildren: () => import('./main/main.module').then(m => m.MainModule)
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
