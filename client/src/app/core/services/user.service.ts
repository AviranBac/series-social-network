import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../environments/environment";
import {User} from "../../main/shared/models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUserDetails(username: string): Observable<User> {
    return this.http.get<User>(`${environment.apiGatewayUrl}/data/users/${username}`);
  }
}
