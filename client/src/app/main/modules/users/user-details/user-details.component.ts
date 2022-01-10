import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/services/user.service";
import {User} from "../../../shared/models/user";
import {
  BehaviorSubject,
  combineLatest,
  filter,
  map,
  Observable,
  Subject,
  switchMap,
  take, takeUntil,
  tap,
  withLatestFrom
} from "rxjs";
import * as RouterSelectors from "../../../root-store/router/router.selectors";
import * as UserSelectors from "../../../root-store/user/user.selectors";
import {RouterState} from "@ngrx/router-store";
import {Store} from "@ngrx/store";
import * as UserState from '../../../root-store/user/user.state';
import {FollowService} from "../../../../core/services/follow.service";
import {Destroyable} from "../../../shared/utils/destroyable.utils";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.less']
})
export class UserDetailsComponent extends Destroyable implements OnInit {
  user$: Observable<User>;
  username$: Observable<string>;
  isLoggedUser$: Observable<boolean>;
  isFollowing$: Subject<boolean> = new BehaviorSubject<boolean>(false);

  private loggedUsername$: Observable<string>;

  constructor(private userService: UserService,
              private followService: FollowService,
              private routerStore: Store<RouterState>,
              private userStore: Store<UserState.State>) {
    super();
  }

  ngOnInit(): void {
    this.username$ = this.routerStore.select(RouterSelectors.selectCurrentUsername).pipe(
      filter(username => !!username)
    );

    this.user$ = this.username$.pipe(
      switchMap(username => this.userService.loadUserDetails(username))
    );

    this.loggedUsername$ = this.selectUsername();

    this.isLoggedUser$ = this.username$.pipe(
      withLatestFrom(this.loggedUsername$),
      map(([displayedUsernameInPage, loggedInUsername]) => displayedUsernameInPage === loggedInUsername)
    );

    // Initialize isFollowing subject
    combineLatest([this.loggedUsername$, this.username$]).pipe(
      switchMap(([followingUsername, followedUsername]) => this.followService.isFollowing(followingUsername!, followedUsername)),
      tap(isFollowing => this.isFollowing$.next(isFollowing)),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  updateFollow(): void {
    combineLatest([this.isFollowing$, this.loggedUsername$, this.username$]).pipe(
      take(1),
      switchMap(([isFollowing, loggedUsername, username]) => this.followService.updateFollow(loggedUsername, username, isFollowing))
    ).subscribe((isCurrentlyFollowing: boolean) => this.isFollowing$.next(isCurrentlyFollowing));
  }

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      take(1)
    )
  }
}
