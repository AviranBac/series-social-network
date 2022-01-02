import * as WatchlistState from "./watchlist.state";
import {seriesAdapter} from "./watchlist.state";
import {createFeatureSelector} from "@ngrx/store";

export const selectWatchlistSeriesState = createFeatureSelector<WatchlistState.State>(
  WatchlistState.seriesFeatureKey,
);

export const {
  selectAll,
  selectEntities,
  selectIds,
  selectTotal
} = seriesAdapter.getSelectors(selectWatchlistSeriesState);
