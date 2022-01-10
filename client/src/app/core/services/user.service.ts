import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "../../main/shared/models/user";
import {Page} from "../../main/shared/models/page";

export interface RequestedUserFilter {
  userName: string,
  firstName: string,
  lastName: string
}

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  loadUsersByFilter(page: number, requestedUserFilter: RequestedUserFilter): Observable<Page<User>> {
    const params = {
      page: page - 1,
      userName: requestedUserFilter.userName,
      firstName: requestedUserFilter.firstName,
      lastName: requestedUserFilter.lastName
    };

    return this.http.get<Page<User>>(`${environment.apiGatewayUrl}/data/users`, { params });
  }

  loadUserDetails(username: string): Observable<User> {
    return this.http.get<User>(`${environment.apiGatewayUrl}/data/users/${username}`);
  }
}
