import {createAction, props} from '@ngrx/store';
import {WatchlistTvSeasonEntity, WatchlistTvSeriesEntity} from "./watchlist.state";
import {Dictionary} from "@ngrx/entity";
import {TvEpisode} from "../../shared/models/tv-episode";

export const loadWatchlist = createAction(
  "[Watchlist] Load Watchlist"
);

export const loadWatchlistSeriesSuccess = createAction(
  "[Watchlist] Load Watchlist Series Success",
  props<{ normalizedSeries: Dictionary<WatchlistTvSeriesEntity> }>()
);

export const loadWatchlistSeasonsSuccess = createAction(
  "[Watchlist] Load Watchlist Seasons Success",
  props<{ normalizedSeasons: Dictionary<WatchlistTvSeasonEntity> }>()
);

export const loadWatchlistEpisodesSuccess = createAction(
  "[Watchlist] Load Watchlist Episodes Success",
  props<{ normalizedEpisodes: Dictionary<TvEpisode> }>()
);



