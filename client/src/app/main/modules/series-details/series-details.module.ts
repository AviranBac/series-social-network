import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SeriesDetailsComponent} from './series-details/series-details.component';
import {RouterModule} from "@angular/router";
import {routes} from "./serie-details.routing";
import {MatIconModule} from "@angular/material/icon";
import {MatSelectModule} from "@angular/material/select";
import {FormsModule} from "@angular/forms";
import {SeasonDetailsComponent} from './season-details/season-details.component';
import {MatCardModule} from "@angular/material/card";
import {EntityCardComponent} from './entity-card/entity-card.component';
import {SharedModule} from "../../shared/shared.module";
import {HomeModule} from "../home/home.module";

@NgModule({
  declarations: [
    SeriesDetailsComponent,
    SeasonDetailsComponent,
    EntityCardComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
        MatIconModule,
        MatSelectModule,
        FormsModule,
        MatCardModule,
        SharedModule,
        HomeModule
    ]
})
export class SeriesDetailsModule { }
