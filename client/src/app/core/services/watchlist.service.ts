import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {TvSeries} from "../../shared/models/tv-series";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor(private http: HttpClient) { }

  getWatchlistSeries(username: string): Observable<TvSeries[]> {
    console.log('hello');
    return this.http.get<TvSeries[]>(`${environment.apiGatewayUrl}/data/watchlist/${username}/series`);
  }
}
