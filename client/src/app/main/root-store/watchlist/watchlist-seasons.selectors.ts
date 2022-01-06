import * as WatchlistState from "./watchlist.state";
import {TvSeasonState} from "./watchlist.state";
import {createFeatureSelector, createSelector} from "@ngrx/store";
import {WatchlistStatus} from "../../shared/models/watchlist-status";
import {selectSeries} from "./watchlist-series.selectors";
import {selectWatchlistEpisodeState} from "./watchlist-episodes.selectors";
import {extractSeasonsAndEpisodes} from "../../shared/normalizers/watchlist-normalizer";

export const selectWatchlistSeasonState = createFeatureSelector<TvSeasonState>(
  WatchlistState.seasonsFeatureKey
);

export const selectSeasonsBySeries = (seriesId: string) => createSelector(
  selectSeries(seriesId),
  selectWatchlistSeasonState,
  selectWatchlistEpisodeState,
  (series, allWatchlistSeasons, allWatchlistEpisodes) => {
    return extractSeasonsAndEpisodes(series, allWatchlistSeasons, allWatchlistEpisodes);
  }
)

export const selectWatchlistStatus = (seasonId: string) => createSelector(
  selectWatchlistSeasonState,
  entities => entities[seasonId]?.watchlistStatus || WatchlistStatus.NONE
);
