import {Component, Input} from '@angular/core';
import {filter, map, Observable, of, switchMap, tap, withLatestFrom} from 'rxjs';
import {FollowService} from "../../../../core/services/follow.service";
import {extractUserRouterLink, User} from "../../../shared/models/user";
import {Page} from "../../../shared/models/page";
import {ColumnDetails, userColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";
import {Store} from "@ngrx/store";
import * as UserState from "../../../root-store/user/user.state";
import * as UserSelectors from "../../../root-store/user/user.selectors";

@Component({
  selector: 'app-user-following',
  templateUrl: './user-following.component.html',
  styleUrls: ['./user-following.component.less']
})
export class UserFollowingComponent {
  @Input() username$: Observable<string>;
  @Input() isCurrentUser$: Observable<boolean>;

  columnDetails: ColumnDetails[] = userColumnDetails;
  itemsPerPage: number = 10;

  constructor(private followService: FollowService,
              private userStore: Store<UserState.State>) {}

  getRequestFn(): Observable<(page: number) => Observable<Page<User>>> {
    return of((page: number) => this.username$.pipe(
      switchMap(username => this.followService.loadFollowing(username, page))
    ));
  }

  getRemoveRequestFn(): Observable<(followedUser: User) => Observable<boolean>> {
    return of((followedUser: User) => this.username$.pipe(
      switchMap((followingUsername: string) => this.followService.updateFollow(followingUsername, followedUser.userName, true))
    ));
  }

  getUserDetailsRoute(user: User) {
    return extractUserRouterLink(user);
  }

  canRemoveEntity(): Observable<boolean> {
    return this.username$.pipe(
      withLatestFrom(this.userStore.select(UserSelectors.selectUsername).pipe(
        filter(username => !!username),
        map(username => username as string),
      )),
      tap(console.log),
      map(([username, loggedInUsername]) => username === loggedInUsername)
    );
  }
}
