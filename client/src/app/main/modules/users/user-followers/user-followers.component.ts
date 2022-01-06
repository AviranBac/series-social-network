import {Component, Input} from '@angular/core';
import {ColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";
import {FollowService} from "../../../../core/services/follow.service";
import {Observable, switchMap} from "rxjs";
import {Page} from "../../../shared/models/page";
import {User} from "../../../shared/models/user";

@Component({
  selector: 'app-user-followers',
  templateUrl: './user-followers.component.html',
  styleUrls: ['./user-followers.component.less']
})
export class UserFollowersComponent {
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
