import {episodesInitialState, seasonsInitialState, seriesInitialState} from "./watchlist.state";
import {createReducer, on} from "@ngrx/store";
import {
  upsertWatchlistEpisodesSuccess,
  upsertWatchlistSeasonsSuccess,
  upsertWatchlistSeriesSuccess
} from "./watchlist.actions";

export const seriesReducer = createReducer(
  seriesInitialState,
  on(upsertWatchlistSeriesSuccess, (state, action) => ({
    ...action.normalizedSeries
  }))
);

export const seasonsReducer = createReducer(
  seasonsInitialState,
  on(upsertWatchlistSeasonsSuccess, (state, action) => ({
    ...action.normalizedSeasons
  }))
);

export const episodesReducer = createReducer(
  episodesInitialState,
  on(upsertWatchlistEpisodesSuccess, (state, action) => ({
    ...action.normalizedEpisodes
  }))
);
