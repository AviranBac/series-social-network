import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Store} from "@ngrx/store";
import * as UserState from '../../root-store/user/user.state';
import * as UserActions from '../../root-store/user/user.actions';
import {catchError, mapTo, Observable, of, tap} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "../../shared/models/user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient,
              private userStore: Store<UserState.State>) {}

  isAuthenticated(): Observable<boolean> {
    return this.http.get<User>(`${environment.apiGatewayUrl}/data/users/self`).pipe(
      tap((user: User) => this.userStore.dispatch(UserActions.upsertActiveUser({ user }))),
      mapTo(true),
      catchError(error => {
        console.error(error);
        return of(false);
      })
    )
  }
}
