import {Component} from '@angular/core';
import {Observable, of} from "rxjs";
import {Page} from "../../../shared/models/page";
import {TvSeries} from "../../../shared/models/tv-series";
import {FollowService} from "../../../../core/services/follow.service";
import {TvSeriesService} from "../../../../core/services/tv-series.service";
import {User} from "../../../shared/models/user";
import {ColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";

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
              private tvSeriesService: TvSeriesService) {
    const seriesColumnDetails: ColumnDetails[] = [
      { field: 'name', label: 'Name' },
      { field: 'numberOfEpisodes', label: 'Number of Episodes' },
      { field: 'numberOfSeasons', label: 'Number of Seasons' },
      { field: 'status', label: 'Series Status' },
      { field: 'genres', label: 'Genres' }
    ];
    const seriesImageSrcExtractor = (entity: TvSeries | User) => (entity as TvSeries).posterPath;
    const seriesRouterLinkExtractor = (entity: TvSeries | User) => `/series/${(entity as TvSeries).id}`;
    const userRouterLinkExtractor = (entity: TvSeries | User) => `/users/${(entity as User).userName}`;

    this.tabsAndRequestFn = [
      {
        label: 'Most Watched Series',
        requestFn$: of(page => this.tvSeriesService.loadMostWatchedSeries(page, 'DESC')),
        columnDetails: seriesColumnDetails,
        imageSrcExtractor: seriesImageSrcExtractor,
        routerLinkExtractor: seriesRouterLinkExtractor
      },
      { label: 'Least Watched Series',
        requestFn$: of(page => this.tvSeriesService.loadMostWatchedSeries(page, 'ASC')),
        columnDetails: seriesColumnDetails,
        imageSrcExtractor: seriesImageSrcExtractor,
        routerLinkExtractor: seriesRouterLinkExtractor
      },
      { label: 'Top Rated Series',
        requestFn$: of(page => this.tvSeriesService.loadTopRatedSeries(page)),
        columnDetails: seriesColumnDetails,
        imageSrcExtractor: seriesImageSrcExtractor,
        routerLinkExtractor: seriesRouterLinkExtractor
      },
      { label: 'Most Popular Series',
        requestFn$: of(page => this.tvSeriesService.loadMostPopularSeries(page)),
        columnDetails: seriesColumnDetails,
        imageSrcExtractor: seriesImageSrcExtractor,
        routerLinkExtractor: seriesRouterLinkExtractor
      },
      { label: 'Most Followed Users',
        requestFn$: of(page => this.followService.loadMostFollowedUsers(page, 'DESC')),
        columnDetails: [
          {field: 'userName', label: 'User Name'},
          {field: 'firstName', label: 'First Name'},
          {field: 'lastName', label: 'Last Name'}
        ],
        routerLinkExtractor: userRouterLinkExtractor
      },
    ]
  }
}
