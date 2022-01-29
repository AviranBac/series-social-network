import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActionReducerMap, StoreModule} from "@ngrx/store";
import * as UserState from './user/user.state';
import * as UserReducer from './user/user.reducer';
import {environment} from "../../../environments/environment";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {routerReducer, RouterState, StoreRouterConnectingModule} from "@ngrx/router-store";
import {EffectsModule} from "@ngrx/effects";

export interface State {
  user: UserState.State,
  router: RouterState
}

const reducers: ActionReducerMap<State> = {
  user: UserReducer.userReducer,
  router: routerReducer
};

const initialState: State = {
  user: UserState.initialState,
  router: RouterState.Minimal
};

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forRoot(reducers),
    StoreRouterConnectingModule.forRoot(),
    EffectsModule.forRoot([]),
    !environment.production ? StoreDevtoolsModule.instrument() : []
  ]
})
export class RootStoreModule { }
