import {User} from "../../shared/models/user";

export const userFeatureKey = 'user';

export interface State {
  activeUser: User | undefined
}

export const initialState: State = {
  activeUser: undefined
};
