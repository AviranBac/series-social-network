import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../../core/services/user.service";
import {User} from "../../../shared/models/user";
import {map, Observable, switchMap, withLatestFrom} from "rxjs";
import * as RouterSelectors from "../../../root-store/router/router.selectors";
import * as UserSelectors from "../../../root-store/user/user.selectors";
import {RouterState} from "@ngrx/router-store";
import {Store} from "@ngrx/store";
import * as UserState from '../../../root-store/user/user.state';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.less']
})
export class UserDetailsComponent implements OnInit {
  user$: Observable<User>;
  username$: Observable<string>;
  isCurrentUser$: Observable<boolean>;

  constructor(private userService: UserService,
              private routerStore: Store<RouterState>,
              private userStore: Store<UserState.State>) { }

  ngOnInit(): void {
    this.username$ = this.routerStore.select(RouterSelectors.selectCurrentUsername);
    this.user$ = this.username$.pipe(
      switchMap(username => this.userService.getUserDetails(username))
    );

    this.isCurrentUser$ = this.username$.pipe(
      withLatestFrom(this.userStore.select(UserSelectors.selectUsername)),
      map(([displayedUsernameInPage, loggedInUsername]) => displayedUsernameInPage === loggedInUsername)
    );
  }
}
