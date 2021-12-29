import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import {RouterState} from "@ngrx/router-store";
import {ExtendedTvSeries} from "../../../shared/models/tv-series";
import {filter, map, Observable, Subject, switchMap, withLatestFrom} from "rxjs";
import {TvSeriesService} from "../../../core/services/tv-series.service";
import * as RouterSelectors from "../../../root-store/router/router.selectors";
import {getStatusValue} from 'src/app/shared/models/series-status';
import {TvSeason} from "../../../shared/models/tv-season";
import {find} from 'lodash';

@Component({
  selector: 'app-series-details',
  templateUrl: './series-details.component.html',
  styleUrls: ['./series-details.component.less']
})
export class SeriesDetailsComponent {
  tvSeries$: Observable<ExtendedTvSeries>;
  selectedTvSeasonTrigger$: Subject<string> = new Subject<string>();
  selectedTvSeason$: Observable<TvSeason>;

  constructor(private routerStore: Store<RouterState>,
              private tvSeriesService: TvSeriesService) {
    this.tvSeries$ = this.routerStore.select(RouterSelectors.selectRouteParam('seriesId')).pipe(
      filter(seriesId => !!seriesId),
      map(seriesId => seriesId as string),
      switchMap(seriesId => this.tvSeriesService.loadSeriesDetails(seriesId))
    );

    this.selectedTvSeason$ = this.selectedTvSeasonTrigger$.pipe(
      withLatestFrom(this.tvSeries$),
      map(([requestedSeasonId, series]) => find(series.seasons, {id: requestedSeasonId})),
      filter(season => !!season),
      map(season => season as TvSeason)
    )
  }

  getStatusValue(status: string) {
    return getStatusValue(status);
  }
}
