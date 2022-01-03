export interface TvEpisode {
  discriminator: 'episode';
  id: string;
  name: string;
  episodeNumber: string;
  seasonNumber: string;
  airDate: string; // TODO
  overview: string;
  stillPath: string;
  voteAverage: number;
  voteCount: number;
}

export function isEpisode(object: any): object is TvEpisode {
  return object.discriminator === 'episode';
}
