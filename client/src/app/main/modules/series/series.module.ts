import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SeriesDetailsComponent} from './series-details/series-details.component';
import {RouterModule} from "@angular/router";
import {routes} from "./series.routing";
import {MatIconModule} from "@angular/material/icon";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {SeasonDetailsComponent} from './season-details/season-details.component';
import {MatCardModule} from "@angular/material/card";
import {EntityCardComponent} from './entity-card/entity-card.component';
import {SharedModule} from "../../shared/shared.module";
import {HomeModule} from "../home/home.module";
import {SearchSeriesComponent} from './search-series/search-series.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatCheckboxModule} from "@angular/material/checkbox";

@NgModule({
  declarations: [
    SeriesDetailsComponent,
    SeasonDetailsComponent,
    EntityCardComponent,
    SearchSeriesComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    MatIconModule,
    MatSelectModule,
    FormsModule,
    MatCardModule,
    SharedModule,
    HomeModule,
    MatSidenavModule,
    MatCheckboxModule,
    ReactiveFormsModule
  ]
})
export class SeriesModule { }
