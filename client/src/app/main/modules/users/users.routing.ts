import {Routes} from "@angular/router";
import {UserDetailsComponent} from "./user-details/user-details.component";
import {UserFollowersComponent} from "./user-followers/user-followers.component";
import {UserFollowingComponent} from "./user-following/user-following.component";
import {SearchUsersComponent} from "./search-users/search-users.component";
import {UpdateDetailsComponent} from "./update-details/update-details.component";

export const routes: Routes = [
  {
    path: 'update',
    component: UpdateDetailsComponent
  },
  {
    path: 'search',
    component: SearchUsersComponent
  },
  {
    path: ':username',
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
