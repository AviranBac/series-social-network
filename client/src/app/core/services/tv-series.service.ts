import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../../shared/models/page";
import {environment} from "../../../environments/environment";
import {ExtendedTvSeries, TvSeries} from "../../shared/models/tv-series";
import {Sort} from "../../shared/models/sort";

@Injectable({
  providedIn: 'root'
})
export class TvSeriesService {

  constructor(private http: HttpClient) { }

  loadSeriesDetails(seriesId: string): Observable<ExtendedTvSeries> {
    return this.http.get<ExtendedTvSeries>(`${environment.apiGatewayUrl}/data/series/${seriesId}`);
  }

  loadMostPopularSeries(page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/popular`, { params });
  }

  loadTopRatedSeries(page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/topRated`, { params });
  }

  loadCommonSeriesAmongFollowing(username: string, page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/commonAmongFollowing/${username}`, { params });
  }

  loadMostWatchedSeries(page: number, sort: Sort): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1,
      sort: sort
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/watched`, { params });
  }
}
