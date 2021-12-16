import {SeriesStatus} from "./series-status";

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
