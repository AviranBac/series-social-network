import {createFeatureSelector, createSelector} from '@ngrx/store';
import * as UserState from './user.state';
import {User} from "../../shared/models/user";

export const selectUserState = createFeatureSelector<UserState.State>(
  UserState.userFeatureKey,
);

export const selectActiveUser = createSelector(
  selectUserState,
  (state: UserState.State) => state?.activeUser
);

export const selectFullName = createSelector(
  selectActiveUser,
  (user: User | undefined) => !!user ? `${user.firstName} ${user.lastName}` : undefined
);

export const selectUsername = createSelector(
  selectActiveUser,
  (user: User | undefined) => !!user ? user.userName : undefined
);
