import {TvSeries} from "../../shared/models/tv-series";
import {createEntityAdapter, EntityAdapter, EntityState} from "@ngrx/entity";
import {TvSeason} from "../../shared/models/tv-season";
import {TvEpisode} from "../../shared/models/tv-episode";

export const seriesFeatureKey = 'watchlistSeries';
export const seasonsFeatureKey = 'seasons';

export const seriesAdapter : EntityAdapter<WatchlistTvSeries> = createEntityAdapter<WatchlistTvSeries>();
export const seasonAdapter : EntityAdapter<WatchlistTvSeason> = createEntityAdapter<WatchlistTvSeason>();
export const episodeAdapter : EntityAdapter<TvEpisode> = createEntityAdapter<TvEpisode>();

export interface WatchlistTvEpisodeState extends EntityState<TvEpisode> {}

export interface WatchlistTvSeason extends Omit<TvSeason, 'episodes'> {
  episodes: WatchlistTvEpisodeState
}
export interface WatchlistTvSeasonState extends EntityState<WatchlistTvSeason> {}

export interface WatchlistTvSeries extends TvSeries {
  [seasonsFeatureKey]: WatchlistTvSeasonState
}
export interface State extends EntityState<WatchlistTvSeries> {}

export const seriesInitialState: State = seriesAdapter.getInitialState();
export const seasonsInitialState: WatchlistTvSeasonState = seasonAdapter.getInitialState();
export const episodesInitialState: WatchlistTvEpisodeState = episodeAdapter.getInitialState();

