import * as WatchlistState from "./watchlist.state";
import {TvSeasonState, WatchlistTvSeasonEntity} from "./watchlist.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {WatchlistStatus} from "../../shared/models/watchlist-status";
import {selectSeries} from "./watchlist-series.selectors";
import {selectWatchlistEpisodeState} from "./watchlist-episodes.selectors";
import {TvEpisode} from "../../shared/models/tv-episode";

export const selectWatchlistSeasonState = createFeatureSelector<TvSeasonState>(
  WatchlistState.seasonsFeatureKey
);

export const selectSeasonsBySeries = (seriesId: string) => createSelector(
  selectSeries(seriesId),
  selectWatchlistSeasonState,
  selectWatchlistEpisodeState,
  (series, allWatchlistSeasons, allWatchlistEpisodes) => {
    const seasonIds: string[] = series?.seasons || [];
    return Object.keys(allWatchlistSeasons)
      .filter(key => seasonIds.includes(key))
      .map(seasonKey => allWatchlistSeasons[seasonKey] as WatchlistTvSeasonEntity)
      .map(season => ({
        ...season,
        episodes: season.episodes.map(episodeId => allWatchlistEpisodes[episodeId] as TvEpisode)
      }));
  }
)

export const selectWatchlistStatus = (seasonId: string) => createSelector(
  selectWatchlistSeasonState,
  entities => entities[seasonId]?.watchlistStatus || WatchlistStatus.NONE
);
