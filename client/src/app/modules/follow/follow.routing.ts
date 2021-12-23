import {Routes} from "@angular/router";
import {YourFollowingComponent} from "./your-following/your-following.component";
import {YourFollowersComponent} from "./your-followers/your-followers.component";

export const routes: Routes = [
  {
    path: ':username/following',
    component: YourFollowingComponent
  },
  {
    path: ':username/followers',
    component: YourFollowersComponent
  }
];
