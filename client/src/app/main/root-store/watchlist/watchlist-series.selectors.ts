import * as WatchlistState from "./watchlist.state";
import {TvSeriesState, WatchlistTvSeriesEntity} from "./watchlist.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {WatchlistStatus} from "../../shared/models/watchlist-status";

export const selectWatchlistSeriesState = createFeatureSelector<TvSeriesState>(
  WatchlistState.seriesFeatureKey
);

export const selectAllSeries = createSelector(
  selectWatchlistSeriesState,
  entities => (entities ? Object.values(entities) : []) as unknown as WatchlistTvSeriesEntity[]
);

export const selectSeries = (seriesId: string) => createSelector(
  selectWatchlistSeriesState,
  entities => entities[seriesId]
);

export const selectWatchlistStatus = (seriesId: string) => createSelector(
  selectSeries(seriesId),
  series => series?.watchlistStatus || WatchlistStatus.NONE
);
