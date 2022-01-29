import * as WatchlistState from "./watchlist.state";
import {TvEpisodeState} from "./watchlist.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {WatchlistStatus} from "../../shared/models/watchlist-status";

export const selectWatchlistEpisodeState = createFeatureSelector<TvEpisodeState>(
  WatchlistState.episodesFeatureKey
);

export const selectWatchlistStatus = (episodeId: string) => createSelector(
  selectWatchlistEpisodeState,
  entities => entities[episodeId] ? WatchlistStatus.COMPLETE : WatchlistStatus.NONE
);
