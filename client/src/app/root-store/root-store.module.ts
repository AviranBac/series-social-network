import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ActionReducerMap, StoreModule} from "@ngrx/store";
import * as UserState from './user/user.state';
import * as UserReducer from './user/user.reducer';
import {environment} from "../../environments/environment";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";

export interface State {
  user: UserState.State
}

const reducers: ActionReducerMap<State> = {
  user: UserReducer.userReducer
};

const initialState: State = {
  user: UserState.initialState
};

@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    StoreModule.forRoot(reducers),
    !environment.production ? StoreDevtoolsModule.instrument() : []
  ]
})
export class RootStoreModule { }
