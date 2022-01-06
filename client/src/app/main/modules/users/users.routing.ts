import {Routes} from "@angular/router";
import {UserDetailsComponent} from "./user-details/user-details.component";
import {UserFollowersComponent} from "./user-followers/user-followers.component";
import {UserFollowingComponent} from "./user-following/user-following.component";

export const routes: Routes = [
  {
    path: ':username',
    component: UserDetailsComponent
  },
  {
    path: 'update',
    component: UserDetailsComponent
  },
  {
    path: ':username/following',
    component: UserFollowingComponent
  },
  {
    path: ':username/followers',
    component: UserFollowersComponent
  }
];
