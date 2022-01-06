import {Dictionary} from "@ngrx/entity";
import {TvEpisode} from "../../shared/models/tv-episode";
import {
  NormalizedWatchlist,
  WatchlistTvSeasonEntity,
  WatchlistTvSeriesEntity
} from "../../shared/normalizers/watchlist-normalizer";

export const seriesFeatureKey = 'series';
export const seasonsFeatureKey = 'seasons';
export const episodesFeatureKey = 'episodes';

export type TvSeriesState = Dictionary<WatchlistTvSeriesEntity>;
export type TvSeasonState = Dictionary<WatchlistTvSeasonEntity>;
export type TvEpisodeState = Dictionary<TvEpisode>;
export type State = NormalizedWatchlist;

export const seriesInitialState: TvSeriesState = {};
export const seasonsInitialState: TvSeasonState = {};
export const episodesInitialState: TvEpisodeState = {};

export const initialState: State = {
  series: seriesInitialState,
  seasons: seasonsInitialState,
  episodes: episodesInitialState
}

