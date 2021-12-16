import {ChangeDetectorRef, Component, OnDestroy} from '@angular/core';
import {MediaMatcher} from "@angular/cdk/layout";
import {Store} from "@ngrx/store";
import * as UserState from '../../../root-store/user/user.state';
import * as UserSelectors from '../../../root-store/user/user.selectors';
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";

interface MenuOptions {
  icon: string,
  value: string,
  routerLink: string
}

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.less']
})
export class NavBarComponent implements OnDestroy {
  mobileQuery: MediaQueryList;
  fullName$: Observable<string | undefined>;
  logoutUrl: string;

  sideNavOptions: MenuOptions[] = [
    { icon: 'home', value: 'Home', routerLink: '/' },
    { icon: 'tv', value: 'Search Series', routerLink: '/series' },
    { icon: 'person', value: 'Search Network', routerLink: '/network' },
    { icon: 'poll', value: 'Statistics', routerLink: '/statistics' },
  ];

  userNavOptions: MenuOptions[] = [
    { icon: 'account_circle', value: 'Update Your Details', routerLink: '/user/update' },
    { icon: 'visibility', value: 'Your Watchlist', routerLink: '/watchlist' },
    { icon: 'people_outline', value: 'Your Following', routerLink: '/following' },
    { icon: 'people_outline', value: 'Your Followers', routerLink: '/followers' },
  ];

  private mobileQueryListener: () => void;

  constructor(private changeDetectorRef: ChangeDetectorRef,
              private media: MediaMatcher,
              private userStore$: Store<UserState.State>) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this.mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this.mobileQueryListener);
    this.logoutUrl = `${environment.apiGatewayUrl}/logout`;
  }

  ngOnInit() {
    this.fullName$ = this.userStore$.select<string | undefined>(UserSelectors.selectFullName);
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this.mobileQueryListener);
  }
}
