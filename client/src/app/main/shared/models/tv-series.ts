import {SeriesStatus} from "./series-status";
import {TvSeason, WatchlistTvSeason} from "./tv-season";
import {WatchlistStatus} from "./watchlist-status";

export interface TvSeries {
  discriminator: 'series';
  id: string,
  name: string,
  firstAirDate: string, // TODO
  originalLanguage: string,
  overview: string,
  posterPath: string,
  popularity: number,
  voteAverage: number,
  voteCount: number,
  numberOfEpisodes: number,
  numberOfSeasons: number,
  status: SeriesStatus,
  genres: string[]
}

export interface ExtendedTvSeries extends TvSeries {
  seasons: TvSeason[]
}

export interface WatchlistTvSeries extends TvSeries {
  seasons: WatchlistTvSeason[],
  watchlistStatus: WatchlistStatus
}

export function isSeries(object: any): object is WatchlistTvSeries {
  return object.discriminator === 'series';
}
