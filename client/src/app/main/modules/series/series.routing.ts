import {Routes} from "@angular/router";
import {SeriesDetailsComponent} from "./series-details/series-details.component";
import {SearchSeriesComponent} from "./search-series/search-series.component";

export const routes: Routes = [
  {
    path: 'search',
    component: SearchSeriesComponent
  },
  {
    path: ':seriesId',
    component: SeriesDetailsComponent
  }
];
