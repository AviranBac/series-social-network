import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StatisticsPageComponent} from './statistics-page/statistics-page.component';
import {RouterModule} from "@angular/router";
import {routes} from "./statistics.routing";
import {MatTabsModule} from "@angular/material/tabs";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
  declarations: [
    StatisticsPageComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatTabsModule,
    SharedModule
  ]
})
export class StatisticsModule { }
