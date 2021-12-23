import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {User} from "../../shared/models/user";
import {environment} from "../../../environments/environment";
import {Page} from "../../shared/models/page";

@Injectable({
  providedIn: 'root'
})
export class FollowService {

  constructor(private http: HttpClient) { }

  loadFollowing(username: string, page: number) {
    const params = {
      page: page
    };

    return this.http.get<Page<User>>(`${environment.apiGatewayUrl}/data/users/${username}/following`, { params });
  }
}
