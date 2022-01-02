import * as WatchlistState from "./watchlist.state";
import {episodeAdapter, seasonAdapter} from "./watchlist.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";

export const selectWatchlistSeasonState = createFeatureSelector<WatchlistState.WatchlistTvSeasonState>(
  WatchlistState.seasonsFeatureKey
);

export const {
  selectAll,
  selectEntities,
  selectIds,
  selectTotal
} = seasonAdapter.getSelectors(selectWatchlistSeasonState);

export const selectSeasons = createSelector(
  selectAll,
  state => state.map(season => ({ ...season, episodes: episodeAdapter.getSelectors().selectAll(season.episodes)}))
);
