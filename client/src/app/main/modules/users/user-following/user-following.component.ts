import {Component, Input} from '@angular/core';
import {Observable, of, switchMap} from 'rxjs';
import {FollowService} from "../../../../core/services/follow.service";
import {extractUserRouterLink, User} from "../../../shared/models/user";
import {Page} from "../../../shared/models/page";
import {ColumnDetails, userColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";

@Component({
  selector: 'app-user-following',
  templateUrl: './user-following.component.html',
  styleUrls: ['./user-following.component.less']
})
export class UserFollowingComponent {
  @Input() username$: Observable<string>;
  @Input() isCurrentUser$: Observable<boolean>;

  columnDetails: ColumnDetails[] = userColumnDetails;
  itemsPerPage: number = 10;

  constructor(private followService: FollowService) {}

  getRequestFn(): Observable<(page: number) => Observable<Page<User>>> {
    return of((page: number) => this.username$.pipe(
      switchMap(username => this.followService.loadFollowing(username, page))
    ));
  }

  getUserDetailsRoute(user: User) {
    return extractUserRouterLink(user);
  }
}
