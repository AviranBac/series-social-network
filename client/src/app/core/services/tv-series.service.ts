import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Page} from "../../main/shared/models/page";
import {environment} from "../../../environments/environment";
import {ExtendedTvSeries, TvSeries} from "../../main/shared/models/tv-series";
import {Sort} from "../../main/shared/models/sort";

@Injectable({
  providedIn: 'root'
})
export class TvSeriesService {

  constructor(private http: HttpClient) { }

  loadSeriesDetails(seriesId: string): Observable<ExtendedTvSeries> {
    return this.http.get<ExtendedTvSeries>(`${environment.apiGatewayUrl}/data/series/${seriesId}`).pipe(
      map(series => this.applyDiscriminators(series))
    );
  }

  loadMostPopularSeries(page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/popular`, { params }).pipe(
      map(page => ({
        ...page,
        content: page.content.map(series => ({ ...series, discriminator: 'series' }))
      }))
    );
  }

  loadTopRatedSeries(page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/topRated`, { params }).pipe(
      map(page => ({
        ...page,
        content: page.content.map(series => ({ ...series, discriminator: 'series' }))
      }))
    );;
  }

  loadCommonSeriesAmongFollowing(username: string, page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/commonAmongFollowing/${username}`, { params }).pipe(
      map(page => ({
        ...page,
        content: page.content.map(series => ({ ...series, discriminator: 'series' }))
      }))
    );
  }

  loadMostWatchedSeries(page: number, sort: Sort): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1,
      sort
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/watched`, { params }).pipe(
      map(page => ({
        ...page,
        content: page.content.map(series => ({ ...series, discriminator: 'series' }))
      }))
    );
  }

  private applyDiscriminators(extendedTvSeries: ExtendedTvSeries): ExtendedTvSeries {
    return {
      ...extendedTvSeries,
      discriminator: 'series',
      seasons: extendedTvSeries.seasons.map(season => ({
        ...season,
        discriminator: 'season',
        episodes: season.episodes.map(episode => ({
          ...episode,
          discriminator: 'episode'
        }))
      }))
    }
  }
}
