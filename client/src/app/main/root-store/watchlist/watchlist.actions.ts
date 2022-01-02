import {createAction, props} from '@ngrx/store';
import {TvSeries} from "../../shared/models/tv-series";
import {TvSeason} from "../../shared/models/tv-season";

export const loadSeries = createAction(
  "[Watchlist] Load Series"
);

export const loadSeriesSuccess = createAction(
  "[Watchlist] Load Series Success",
  props<{ series: TvSeries[] }>()
);

export const loadSeasons = createAction(
  "[Watchlist] Load Seasons",
  props<{ seriesId: string }>()
);

export const loadSeasonsSuccess = createAction(
  "[Watchlist] Load Seasons Success",
  props<{ seasons: TvSeason[] }>()
);



