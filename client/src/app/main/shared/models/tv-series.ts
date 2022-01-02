import {SeriesStatus} from "./series-status";
import {TvSeason} from "./tv-season";

export interface TvSeries {
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
