import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Page} from "../../main/shared/models/page";
import {environment} from "../../../environments/environment";
import {ExtendedTvSeries, TvSeries} from "../../main/shared/models/tv-series";
import {Sort} from "../../main/shared/models/sort";
import {SeriesStatus} from "../../main/shared/models/series-status";
import {TvGenre} from "../../main/shared/models/genre";

export interface SeriesFilterOptions {
  seriesStatuses: SeriesStatus[],
  genres: TvGenre[]
}

export interface RequestedSeriesFilter {
  name: string,
  seriesStatuses: SeriesStatus[],
  genreIds: number[]
}

@Injectable({
  providedIn: 'root'
})
export class TvSeriesService {

  constructor(private http: HttpClient) { }

  loadSeriesFilterOptions(): Observable<SeriesFilterOptions> {
    return this.http.get<SeriesFilterOptions>(`${environment.apiGatewayUrl}/data/series/filters`);
  }

  loadSeriesByFilter(page: number, requestSeriesFilter: RequestedSeriesFilter): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1,
      name: requestSeriesFilter.name,
      seriesStatus: requestSeriesFilter.seriesStatuses,
      genreId: requestSeriesFilter.genreIds
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series`, { params }).pipe(
      map(this.applyDiscriminatorsToPage)
    );
  }

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
      map(this.applyDiscriminatorsToPage)
    );
  }

  loadTopRatedSeries(page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/topRated`, { params }).pipe(
      map(this.applyDiscriminatorsToPage)
    );
  }

  loadCommonSeriesAmongFollowing(username: string, page: number): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/commonAmongFollowing/${username}`, { params }).pipe(
      map(this.applyDiscriminatorsToPage)
    );
  }

  loadMostWatchedSeries(page: number, sort: Sort): Observable<Page<TvSeries>> {
    const params = {
      page: page - 1,
      sort
    };

    return this.http.get<Page<TvSeries>>(`${environment.apiGatewayUrl}/data/series/watched`, { params }).pipe(
      map(this.applyDiscriminatorsToPage)
    );
  }

  private applyDiscriminatorsToPage(seriesPage: Page<TvSeries>): Page<TvSeries> {
    return {
      ...seriesPage,
      content: seriesPage.content.map(series => ({ ...series, discriminator: 'series' }))
    };
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
