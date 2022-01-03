import {TvEpisode} from "./tv-episode";
import {WatchlistStatus} from "./watchlist-status";

export interface TvSeason {
  discriminator: 'season';
  id: string;
  name: string;
  seasonNumber: string;
  airDate: string; // TODO
  overview: string;
  posterPath: string;
  episodes: TvEpisode[];
}

export interface WatchlistTvSeason extends TvSeason {
  watchlistStatus: WatchlistStatus
}

export function isSeason(object: any): object is WatchlistTvSeason {
  return object.discriminator === 'season';
}
