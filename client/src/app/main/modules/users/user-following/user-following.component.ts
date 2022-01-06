import {Component, Input} from '@angular/core';
import {Observable, switchMap} from 'rxjs';
import {FollowService} from "../../../../core/services/follow.service";
import {User} from "../../../shared/models/user";
import {Page} from "../../../shared/models/page";
import {ColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";

@Component({
  selector: 'app-user-following',
  templateUrl: './user-following.component.html',
  styleUrls: ['./user-following.component.less']
})
export class UserFollowingComponent {
  @Input() username$: Observable<string>;
  @Input() isCurrentUser$: Observable<boolean>;

  columnDetails: ColumnDetails[] = [
    {field: 'userName', label: 'User Name'},
    {field: 'firstName', label: 'First Name'},
    {field: 'lastName', label: 'Last Name'}
  ];
  itemsPerPage: number = 10;

  constructor(private followService: FollowService) {}

  getRequestFn(): (page: number) => Observable<Page<User>> {
    return (page: number) => this.username$.pipe(
      switchMap(username => this.followService.loadFollowing(username, page))
    );
  }

  getUserDetailsRoute(user: User) {
    return `/users/${user.userName}`;
  }
}
