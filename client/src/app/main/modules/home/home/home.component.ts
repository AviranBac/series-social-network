import {Component} from '@angular/core';
import {TvSeries} from "../../../shared/models/tv-series";
import {filter, map, Observable, switchMap} from "rxjs";
import {TvSeriesService} from "../../../../core/services/tv-series.service";
import {Page} from "../../../shared/models/page";
import {Store} from "@ngrx/store";
import * as UserState from "../../../root-store/user/user.state";
import * as UserSelectors from "../../../root-store/user/user.selectors";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.less']
})
export class HomeComponent {

  constructor(private tvSeriesService: TvSeriesService,
              private userStore: Store<UserState.State>) {}

  loadFiveMostPopularSeries(): Observable<TvSeries[]> {
     return this.slicePage(this.tvSeriesService.loadMostPopularSeries(1));
  }

  loadFiveCommonSeriesAmongFollowing(): Observable<TvSeries[]> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap(username => this.slicePage(this.tvSeriesService.loadCommonSeriesAmongFollowing(username, 1)))
    );
  }

  private slicePage(page$: Observable<Page<TvSeries>>): Observable<TvSeries[]> {
    return page$.pipe(
      map((page: Page<TvSeries>) => page.content.slice(0, 5))
    );
  }
}
