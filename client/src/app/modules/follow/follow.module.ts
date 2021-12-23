import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import { YourFollowingComponent } from './your-following/your-following.component';
import { RouterModule } from '@angular/router';
import {routes} from "./follow.routing";
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  declarations: [
    YourFollowingComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    NgxPaginationModule
  ]
})
export class FollowModule { }
