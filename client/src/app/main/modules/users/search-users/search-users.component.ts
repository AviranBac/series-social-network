import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ColumnDetails, userColumnDetails} from "../../../shared/components/pagination-table/pagination-table.component";
import {BehaviorSubject, debounceTime, distinctUntilChanged, map, Observable, Subject, takeUntil, tap} from "rxjs";
import {Page} from "../../../shared/models/page";
import {extractUserRouterLink, User} from "../../../shared/models/user";
import {Destroyable} from "../../../shared/utils/destroyable.utils";
import {RequestedUserFilter, UserService} from "../../../../core/services/user.service";

@Component({
  selector: 'app-search-users',
  templateUrl: './search-users.component.html',
  styleUrls: ['./search-users.component.less']
})
export class SearchUsersComponent extends Destroyable implements OnInit {
  form: FormGroup;
  userColumnDetails: ColumnDetails[] = userColumnDetails;
  userRouterLinkExtractor: (user: User) => string = extractUserRouterLink;
  requestFn$: Observable<(page: number) => Observable<Page<User>>>;

  private userFilters$: Subject<RequestedUserFilter>;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService) {
    super();
    this.form = this.formBuilder.group({
      userName: new FormControl(''),
      firstName: new FormControl(''),
      lastName: new FormControl('')
    });
    this.userFilters$ = new BehaviorSubject(this.form.value);
  }

  ngOnInit(): void {
    this.requestFn$ = this.userFilters$.pipe(
      tap(console.log),
      map((requestedUserFilter: RequestedUserFilter) => (page: number) => this.userService.loadUsersByFilter(page, requestedUserFilter)),
      takeUntil(this.destroy$)
    );

    // Listen to form changes after first initialization
    this.form.valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(500),
      takeUntil(this.destroy$)
    ).subscribe(changes => this.userFilters$.next(changes));
  }
}
