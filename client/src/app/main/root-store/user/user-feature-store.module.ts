import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {StoreModule} from "@ngrx/store";
import {reducer} from "./user.reducer";
import {userFeatureKey} from "./user.state";

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forFeature(userFeatureKey, reducer),
  ]
})
export class UserFeatureStoreModule { }
