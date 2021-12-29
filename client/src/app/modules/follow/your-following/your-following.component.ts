import {Component} from '@angular/core';
import {filter, map, Observable, switchMap} from 'rxjs';
import {FollowService} from "../../../core/services/follow.service";
import {User} from "../../../shared/models/user";
import {Page} from "../../../shared/models/page";
import {ColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";
import {Store} from "@ngrx/store";
import {RouterState} from "@ngrx/router-store";
import * as RouterSelectors from '../../../root-store/router/router.selectors';

@Component({
  selector: 'app-following',
  templateUrl: './your-following.component.html',
  styleUrls: ['./your-following.component.less']
})
export class YourFollowingComponent {
  columnDetails: ColumnDetails[] = [
    {field: 'userName', label: 'User Name'},
    {field: 'firstName', label: 'First Name'},
    {field: 'lastName', label: 'Last Name'}
  ];
  itemsPerPage: number = 10;

  constructor(private followService: FollowService,
              private routerStore: Store<RouterState>) {}

  getRequestFn(): (page: number) => Observable<Page<User>> {
    return (page: number) => this.routerStore.select(RouterSelectors.selectRouteParam('username')).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap(username => this.followService.loadFollowing(username, page))
    );
  }
}
