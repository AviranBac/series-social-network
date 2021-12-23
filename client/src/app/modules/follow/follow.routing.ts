import {Routes} from "@angular/router";
import {YourFollowingComponent} from "./your-following/your-following.component";
import {AppComponent} from "../../app.component";

export const routes: Routes = [
  {
    path: ':username/following',
    component: YourFollowingComponent
  },
  {
    path: ':username/follower',
    component: AppComponent
  }
];
