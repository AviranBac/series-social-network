import {episodeAdapter, seasonAdapter, seasonsInitialState, seriesAdapter, seriesInitialState} from "./watchlist.state";
import {createReducer, on} from "@ngrx/store";
import {loadSeasonsSuccess, loadSeriesSuccess} from "./watchlist.actions";

export const seriesReducer = createReducer(
  seriesInitialState,
  on(loadSeriesSuccess, (state, action) =>
    seriesAdapter.setAll(action.series.map(series => ({...series, seasons: seasonAdapter.getInitialState()})), state)
  )
);

export const seasonReducer = createReducer(
  seasonsInitialState,
  on(loadSeasonsSuccess, (state, action) => seasonAdapter.setAll(action.seasons.map(season => ({
      ...season,
      episodes: episodeAdapter.setAll(season.episodes, episodeAdapter.getInitialState())
    })), state)
  )
);
