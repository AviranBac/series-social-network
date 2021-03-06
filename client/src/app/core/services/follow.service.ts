import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from "../../main/shared/models/user";
import {environment} from "../../../environments/environment";
import {Page} from "../../main/shared/models/page";
import {map, Observable} from "rxjs";
import {Sort} from "../../main/shared/models/sort";

enum ActionType {
  ADD = 'ADD',
  REMOVE = 'REMOVE'
}

@Injectable({
  providedIn: 'root'
})
export class FollowService {

  constructor(private http: HttpClient) { }

  isFollowing(followingUsername: string, followedUsername: string): Observable<boolean> {
    return this.http.get<boolean>(`${environment.apiGatewayUrl}/data/users/${followingUsername}/following/${followedUsername}`);
  }

  loadMostFollowedUsers(page: number, sort: Sort): Observable<Page<User>> {
    const params = {
      page: page - 1,
      sort
    };

    return this.http.get<Page<User>>(`${environment.apiGatewayUrl}/data/users/followed`, { params });
  }

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

  updateFollow(followingUsername: string, followedUsername: string, isCurrentlyFollowing: boolean): Observable<boolean> {
    const requestBody = {
      action: isCurrentlyFollowing ? ActionType.REMOVE : ActionType.ADD,
      usernameFrom: followingUsername,
      usernameTo: followedUsername
    };

    return this.http.post<any>(`${environment.apiGatewayUrl}/user-actions/follows`, requestBody).pipe(
      map(() => !isCurrentlyFollowing)
    );
  }
}
