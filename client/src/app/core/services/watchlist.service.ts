import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {WatchlistTvSeries} from "../../main/shared/models/tv-series";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor(private http: HttpClient) { }

  getWatchlistSeries(username: string): Observable<WatchlistTvSeries[]> {
    return this.http.get<WatchlistTvSeries[]>(`${environment.apiGatewayUrl}/data/watchlist/${username}`);
  }
}
