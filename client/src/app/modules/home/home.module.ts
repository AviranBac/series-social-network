import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomeComponent} from './home/home.component';
import {RouterModule} from "@angular/router";
import {routes} from "./home.routing";
import {SeriesFirstPageDisplayComponent} from './series-first-page-display/series-first-page-display.component';
import {MatCardModule} from "@angular/material/card";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    HomeComponent,
    SeriesFirstPageDisplayComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes),
    MatCardModule
  ]
})
export class HomeModule { }
