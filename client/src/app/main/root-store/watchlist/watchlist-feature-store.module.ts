import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from "@ngrx/store";
import {episodesReducer, seasonsReducer, seriesReducer} from "./watchlist.reducer";
import {episodesFeatureKey, seasonsFeatureKey, seriesFeatureKey} from "./watchlist.state";
import {EffectsModule} from "@ngrx/effects";
import {WatchlistEffects} from "./watchlist.effects";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature(seriesFeatureKey, seriesReducer),
    StoreModule.forFeature(seasonsFeatureKey, seasonsReducer),
    StoreModule.forFeature(episodesFeatureKey, episodesReducer),
    EffectsModule.forFeature([WatchlistEffects])
  ]
})
export class WatchlistFeatureStoreModule { }
