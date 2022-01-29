import {Component} from '@angular/core';
import {filter, map, Observable, of, take} from "rxjs";
import {Page} from "../../../shared/models/page";
import {extractSeriesImageSrc, extractSeriesRouterLink, TvSeries} from "../../../shared/models/tv-series";
import {FollowService} from "../../../../core/services/follow.service";
import {TvSeriesService} from "../../../../core/services/tv-series.service";
import {extractUserRouterLink, User} from "../../../shared/models/user";
import {
  ColumnDetails,
  seriesColumnDetails,
  userColumnDetails
} from "../../../shared/components/pagination-table/pagination-table.component";
import {Store} from "@ngrx/store";
import * as UserState from "../../../root-store/user/user.state";
import * as UserSelectors from "../../../root-store/user/user.selectors";

interface TabsAndRequestFn<T> {
  label: string,
  requestFn$: Observable<(page: number) => Observable<Page<T>>>,
  columnDetails: ColumnDetails[]
  imageSrcExtractor?: (entity: T) => string
  routerLinkExtractor?: (entity: T) => string
}

@Component({
  selector: 'app-statistics-page',
  templateUrl: './statistics-page.component.html',
  styleUrls: ['./statistics-page.component.less']
})
export class StatisticsPageComponent {
  tabsAndRequestFn: TabsAndRequestFn<TvSeries | User>[];

  constructor(private followService: FollowService,
              private tvSeriesService: TvSeriesService,
              private userStore: Store<UserState.State>) {
    this.selectUsername().subscribe((username: string) => {
      this.tabsAndRequestFn = [
        {
          label: 'Series You Might Like',
          requestFn$: of(page => this.tvSeriesService.loadCommonSeriesAmongFollowing(username, page)),
          columnDetails: seriesColumnDetails,
          imageSrcExtractor: (entity: TvSeries | User) => extractSeriesImageSrc(entity as TvSeries),
          routerLinkExtractor: (entity: TvSeries | User) => extractSeriesRouterLink(entity as TvSeries)
        },
        {
          label: 'Most Watched Series',
          requestFn$: of(page => this.tvSeriesService.loadMostWatchedSeries(page, 'DESC')),
          columnDetails: seriesColumnDetails,
          imageSrcExtractor: (entity: TvSeries | User) => extractSeriesImageSrc(entity as TvSeries),
          routerLinkExtractor: (entity: TvSeries | User) => extractSeriesRouterLink(entity as TvSeries)
        },
        {
          label: 'Least Watched Series',
          requestFn$: of(page => this.tvSeriesService.loadMostWatchedSeries(page, 'ASC')),
          columnDetails: seriesColumnDetails,
          imageSrcExtractor: (entity: TvSeries | User) => extractSeriesImageSrc(entity as TvSeries),
          routerLinkExtractor: (entity: TvSeries | User) => extractSeriesRouterLink(entity as TvSeries)
        },
        {
          label: 'Top Rated Series',
          requestFn$: of(page => this.tvSeriesService.loadTopRatedSeries(page)),
          columnDetails: seriesColumnDetails,
          imageSrcExtractor: (entity: TvSeries | User) => extractSeriesImageSrc(entity as TvSeries),
          routerLinkExtractor: (entity: TvSeries | User) => extractSeriesRouterLink(entity as TvSeries)
        },
        {
          label: 'Most Popular Series',
          requestFn$: of(page => this.tvSeriesService.loadMostPopularSeries(page)),
          columnDetails: seriesColumnDetails,
          imageSrcExtractor: (entity: TvSeries | User) => extractSeriesImageSrc(entity as TvSeries),
          routerLinkExtractor: (entity: TvSeries | User) => extractSeriesRouterLink(entity as TvSeries)
        },
        {
          label: 'Most Followed Users',
          requestFn$: of(page => this.followService.loadMostFollowedUsers(page, 'DESC')),
          columnDetails: userColumnDetails,
          routerLinkExtractor: (entity: TvSeries | User) => extractUserRouterLink(entity as User)
        }
      ];
    });
  }

  private selectUsername(): Observable<string> {
    return this.userStore.select(UserSelectors.selectUsername).pipe(
      filter(username => !!username),
      map(username => username as string),
      take(1)
    )
  }
}
