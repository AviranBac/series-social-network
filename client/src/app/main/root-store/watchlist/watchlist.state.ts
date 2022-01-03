import {WatchlistTvSeries} from "../../shared/models/tv-series";
import {Dictionary} from "@ngrx/entity";
import {WatchlistTvSeason} from "../../shared/models/tv-season";
import {TvEpisode} from "../../shared/models/tv-episode";

export const seriesFeatureKey = 'series';
export const seasonsFeatureKey = 'seasons';
export const episodesFeatureKey = 'episodes';

export interface WatchlistTvSeasonEntity extends Omit<WatchlistTvSeason, 'episodes'> {
  episodes: string[]
}
export interface WatchlistTvSeriesEntity extends Omit<WatchlistTvSeries, 'seasons'> {
  seasons: string[]
}

export type TvSeriesState = Dictionary<WatchlistTvSeriesEntity>;
export type TvSeasonState = Dictionary<WatchlistTvSeasonEntity>;
export type TvEpisodeState = Dictionary<TvEpisode>;

export interface State {
  [seriesFeatureKey]: TvSeriesState,
  [seasonsFeatureKey]: TvSeasonState,
  [episodesFeatureKey]: TvEpisodeState
}

export const seriesInitialState: TvSeriesState = {};
export const seasonsInitialState: TvSeasonState = {};
export const episodesInitialState: TvEpisodeState = {};

export const initialState: State = {
  series: seriesInitialState,
  seasons: seasonsInitialState,
  episodes: episodesInitialState
}

