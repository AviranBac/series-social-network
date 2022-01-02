import {Action, createReducer, on} from '@ngrx/store';
import * as UserState from "./user.state";
import {initialState, State} from "./user.state";
import * as UserActions from './user.actions';

export const userReducer = createReducer(
  initialState,
  on(UserActions.upsertActiveUser, (state: UserState.State, { user }) => ({ ...state, activeUser: user }))
);

export function reducer(state: State | undefined, action: Action): any {
  return userReducer(state, action);
}
