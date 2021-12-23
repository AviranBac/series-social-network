import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, map, Observable, tap } from 'rxjs';
import {FollowService} from "../../../core/services/follow.service";
import {User} from "../../../shared/models/user";
import {Page} from "../../../shared/models/page";

@Component({
  selector: 'app-following',
  templateUrl: './your-following.component.html',
  styleUrls: ['./your-following.component.less']
})
export class YourFollowingComponent implements OnInit {
  displayedColumns = ['id', 'name', 'email', 'registration'];

  following$: Observable<User[]>;
  p: number = 1;
  totalItems: number;
  loading: boolean;

  constructor(private followService: FollowService) {
  }

  ngOnInit() {
    this.getPage(0);
  }

  getPage(page: number) {
    this.loading = true;
    this.following$ = this.followService.loadFollowing("aviranbac@walla.com", page).pipe(
      tap((response: Page<User>) => {
        this.totalItems = response.totalElements;
        this.p = page;
        this.loading = false;
      }),
      map(response => response.content)
    );
  }
}
