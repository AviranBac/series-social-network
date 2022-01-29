import {createAction, props} from '@ngrx/store';
import {Dictionary} from "@ngrx/entity";
import {TvEpisode} from "../../shared/models/tv-episode";
import {WatchlistTvSeries} from "../../shared/models/tv-series";
import {WatchlistTvSeasonEntity, WatchlistTvSeriesEntity} from "../../shared/normalizers/watchlist-normalizer";

export const loadWatchlist = createAction(
  "[Watchlist] Load Watchlist"
);

export const upsertWatchlistSuccess = createAction(
  "[Watchlist] Load Watchlist Success",
  props<{ series: WatchlistTvSeries[] }>()
);

export const upsertWatchlistSeriesSuccess = createAction(
  "[Watchlist] Load Watchlist Series Success",
  props<{ normalizedSeries: Dictionary<WatchlistTvSeriesEntity> }>()
);

export const upsertWatchlistSeasonsSuccess = createAction(
  "[Watchlist] Load Watchlist Seasons Success",
  props<{ normalizedSeasons: Dictionary<WatchlistTvSeasonEntity> }>()
);

export const upsertWatchlistEpisodesSuccess = createAction(
  "[Watchlist] Load Watchlist Episodes Success",
  props<{ normalizedEpisodes: Dictionary<TvEpisode> }>()
);
