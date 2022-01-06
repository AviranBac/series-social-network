import {WatchlistTvSeries} from "../models/tv-series";
import {normalize, schema} from "normalizr";
import {WatchlistTvSeason} from "../models/tv-season";
import {Dictionary} from "@ngrx/entity";
import {TvEpisode} from "../models/tv-episode";

export interface WatchlistTvSeasonEntity extends Omit<WatchlistTvSeason, 'episodes'> {
  episodes: string[]
}
export interface WatchlistTvSeriesEntity extends Omit<WatchlistTvSeries, 'seasons'> {
  seasons: string[]
}

export interface NormalizedWatchlist {
  series: Dictionary<WatchlistTvSeriesEntity>,
  seasons: Dictionary<WatchlistTvSeasonEntity>,
  episodes: Dictionary<TvEpisode>
}

export function normalizeWatchlistSeries(seriesBeforeNormalization: WatchlistTvSeries[]): NormalizedWatchlist {
  const episodes = new schema.Entity(
    'episodes',
    {},
    { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'episode'})) }
  );
  const seasons = new schema.Entity(
    'seasons',
    { episodes: [episodes] },
    { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'season'})) }
  );
  const series = new schema.Entity(
    'series',
    { seasons: [seasons] },
    { idAttribute: 'id', processStrategy: (value => ({...value, discriminator: 'series'})) }
  );

  return normalize(seriesBeforeNormalization, [series]).entities as unknown as NormalizedWatchlist;
}

export function sortSeries(allNormalizedSeries: Dictionary<WatchlistTvSeriesEntity>): WatchlistTvSeriesEntity[] {
  return !allNormalizedSeries ?
    [] :
    Object.values(allNormalizedSeries).sort((first, second) =>
      first!.name.localeCompare(second!.name)
    ) as unknown as WatchlistTvSeriesEntity[]
}

export function extractSeasonsAndEpisodes(normalizedSeries: WatchlistTvSeriesEntity | undefined,
                                          allWatchlistSeasons: Dictionary<WatchlistTvSeasonEntity>,
                                          allWatchlistEpisodes: Dictionary<TvEpisode>): WatchlistTvSeason[] {
  const seasonIds: string[] = normalizedSeries?.seasons || [];
  return Object.keys(allWatchlistSeasons)
    .filter(key => seasonIds.includes(key))
    .map(seasonKey => allWatchlistSeasons[seasonKey] as WatchlistTvSeasonEntity)
    .map(season => ({
      ...season,
      episodes: season.episodes.map(episodeId => allWatchlistEpisodes[episodeId] as TvEpisode)
    }) as WatchlistTvSeason);
}
