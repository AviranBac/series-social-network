import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from "@ngrx/store";
import {seasonReducer, seriesReducer} from "./watchlist.reducer";
import {seasonsFeatureKey, seriesFeatureKey} from "./watchlist.state";
import {EffectsModule} from "@ngrx/effects";
import {WatchlistEffects} from "./watchlist.effects";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature(seriesFeatureKey, seriesReducer),
    StoreModule.forFeature(seasonsFeatureKey, seasonReducer),
    EffectsModule.forFeature([WatchlistEffects])
  ]
})
export class WatchlistFeatureStoreModule { }
