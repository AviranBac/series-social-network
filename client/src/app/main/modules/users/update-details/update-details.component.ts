import {Component} from '@angular/core';
import {Store} from "@ngrx/store";
import * as UserState from "../../../root-store/user/user.state";
import {UserService} from "../../../../core/services/user.service";
import {filter, map, Observable, switchMap, tap} from "rxjs";
import * as UserSelectors from "../../../root-store/user/user.selectors";
import {User} from "../../../shared/models/user";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import * as UserActions from "../../../root-store/user/user.actions";

@Component({
  selector: 'app-update-details',
  templateUrl: './update-details.component.html',
  styleUrls: ['./update-details.component.less']
})
export class UpdateDetailsComponent {
  userDetails$: Observable<User>;
  form: FormGroup;
  isSwalVisible: boolean = false;

  constructor(private userStore: Store<UserState.State>,
              private userService: UserService,
              private formBuilder: FormBuilder) {
    this.userDetails$ = this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap(username => this.userService.loadUserDetails(username)),
      tap((user: User) => {
        this.form = this.formBuilder.group({
          // TODO: Add validators
          // TODO: disable update button if we are in the initial state
          firstName: new FormControl(user.firstName),
          lastName: new FormControl(user.lastName)
        })
      })
    );
  }

  updateUserDetails(username: string): void {
    const user: User = {
      userName: username,
      firstName: this.form.value.firstName,
      lastName: this.form.value.lastName,
    };

    this.userService.updateUserDetails(user).subscribe((user: User) => {
      this.isSwalVisible = true;
      this.userStore.dispatch(UserActions.upsertActiveUser({ user }));
    });
  }
}
