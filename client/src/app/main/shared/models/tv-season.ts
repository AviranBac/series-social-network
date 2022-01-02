import {TvEpisode} from "./tv-episode";

export interface TvSeason {
  id: string;
  name: string;
  seasonNumber: string;
  airDate: string; // TODO
  overview: string;
  posterPath: string;
  episodes: TvEpisode[];
}
