import {episodesInitialState, seasonsInitialState, seriesInitialState} from "./watchlist.state";
import {createReducer, on} from "@ngrx/store";
import {
  loadWatchlistEpisodesSuccess,
  loadWatchlistSeasonsSuccess,
  loadWatchlistSeriesSuccess
} from "./watchlist.actions";

export const seriesReducer = createReducer(
  seriesInitialState,
  on(loadWatchlistSeriesSuccess, (state, action) => ({
    ...state, ...action.normalizedSeries
  }))
);

export const seasonsReducer = createReducer(
  seasonsInitialState,
  on(loadWatchlistSeasonsSuccess, (state, action) => ({
    ...state, ...action.normalizedSeasons
  }))
);

export const episodesReducer = createReducer(
  episodesInitialState,
  on(loadWatchlistEpisodesSuccess, (state, action) => ({
    ...state, ...action.normalizedEpisodes
  }))
);
