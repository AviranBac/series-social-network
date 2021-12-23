import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {YourFollowingComponent} from './your-following/your-following.component';
import {RouterModule} from '@angular/router';
import {routes} from "./follow.routing";
import {NgxPaginationModule} from 'ngx-pagination';
import {MatTableModule} from "@angular/material/table";
import {SharedModule} from "../../shared/shared.module";
import {YourFollowersComponent} from './your-followers/your-followers.component';

@NgModule({
  declarations: [
    YourFollowingComponent,
    YourFollowersComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgxPaginationModule,
    MatTableModule,
    SharedModule
  ]
})
export class FollowModule { }
