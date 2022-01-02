import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from "../../main/shared/models/user";
import {environment} from "../../../environments/environment";
import {Page} from "../../main/shared/models/page";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class FollowService {

  constructor(private http: HttpClient) { }

  loadFollowing(username: string, page: number): Observable<Page<User>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<User>>(`${environment.apiGatewayUrl}/data/users/${username}/following`, { params });
  }

  loadFollowers(username: string, page: number): Observable<Page<User>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<User>>(`${environment.apiGatewayUrl}/data/users/${username}/followers`, { params });
  }
}
