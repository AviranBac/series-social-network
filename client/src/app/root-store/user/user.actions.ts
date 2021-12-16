import {createAction, props} from '@ngrx/store';
import {User} from "../../shared/models/user";

export const upsertActiveUser = createAction(
  '[User] Upsert Active User',
  props<{ user: User }>()
);




