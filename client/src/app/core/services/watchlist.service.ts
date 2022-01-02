import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {TvSeries} from "../../main/shared/models/tv-series";
import {Observable} from "rxjs";
import {TvSeason} from "../../main/shared/models/tv-season";

@Injectable({
  providedIn: 'root'
})
export class WatchlistService {

  constructor(private http: HttpClient) { }

  getWatchlistSeries(username: string): Observable<TvSeries[]> {
    return this.http.get<TvSeries[]>(`${environment.apiGatewayUrl}/data/watchlist/${username}/series`);
  }

  getWatchlistSeriesDetails(username: string, seriesId: string): Observable<TvSeason[]> {
    return this.http.get<TvSeason[]>(`${environment.apiGatewayUrl}/data/watchlist/${username}/series/${seriesId}`);
  }
}
