import {Routes} from "@angular/router";
import {SeriesDetailsComponent} from "./series-details/series-details.component";

export const routes: Routes = [
  {
    path: ':seriesId',
    component: SeriesDetailsComponent
  }
];
