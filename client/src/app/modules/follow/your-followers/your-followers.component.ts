import {Component} from '@angular/core';
import {ColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";
import {FollowService} from "../../../core/services/follow.service";
import {filter, map, Observable, switchMap} from "rxjs";
import {Page} from "../../../shared/models/page";
import {User} from "../../../shared/models/user";
import * as RouterSelectors from "../../../root-store/router/router.selectors";
import {Store} from "@ngrx/store";
import {RouterState} from "@ngrx/router-store";

@Component({
  selector: 'app-your-followers',
  templateUrl: './your-followers.component.html',
  styleUrls: ['./your-followers.component.less']
})
export class YourFollowersComponent {
  columnDetails: ColumnDetails[] = [
    {field: 'userName', label: 'User Name'},
    {field: 'firstName', label: 'First Name'},
    {field: 'lastName', label: 'Last Name'}
  ];
  itemsPerPage: number = 10;

  constructor(private followService: FollowService,
              private store: Store<RouterState>) {}

  getRequestFn(): (page: number) => Observable<Page<User>> {
    return (page: number) => this.store.select(RouterSelectors.selectRouteParam('username')).pipe(
      filter(username => !!username),
      map(username => username as string),
      switchMap(username => this.followService.loadFollowers(username, page))
    );
  }
}
