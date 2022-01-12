import {Component, Input, OnInit} from '@angular/core';
import {BehaviorSubject, combineLatest, map, Observable, Subject, switchMap, tap} from "rxjs";
import {Page} from "../../models/page";

export interface ColumnDetails {
  field: string
  label: string
}

export const userColumnDetails: ColumnDetails[] = [
  {field: 'userName', label: 'User Name'},
  {field: 'firstName', label: 'First Name'},
  {field: 'lastName', label: 'Last Name'}
];

export const seriesColumnDetails: ColumnDetails[] = [
  { field: 'name', label: 'Name' },
  { field: 'numberOfEpisodes', label: 'Number of Episodes' },
  { field: 'numberOfSeasons', label: 'Number of Seasons' },
  { field: 'status', label: 'Series Status' },
  { field: 'genres', label: 'Genres' }
];

@Component({
  selector: 'app-pagination-table',
  templateUrl: './pagination-table.component.html',
  styleUrls: ['./pagination-table.component.less']
})
export class PaginationTableComponent<T> implements OnInit {
  @Input() paginationId: string;
  @Input() itemsPerPage: number;
  @Input() columnDetails: ColumnDetails[];
  @Input() canRemoveEntity: boolean = false;
  @Input() loadRequestFn$: Observable<(page: number) => Observable<Page<T>>>;
  @Input() removeRequestFn$: Observable<(entity: T) => Observable<any>>;
  @Input() imageSrcExtractor?: (entity: T) => string;
  @Input() routerLinkExtractor?: (entity: T) => string;

  dataSource$: Observable<T[]>;
  pageChange$: Subject<number> = new BehaviorSubject<number>(1);
  positionColumn = 'position';
  imageColumn = 'image';
  removeColumn = 'remove';
  displayedColumns: string[] = [this.positionColumn];
  currentDisplayedPage: number = 1;
  totalItems: number;

  private loading: boolean;
  private entityRemovedTrigger$: Subject<any> = new BehaviorSubject(null);

  constructor() { }

  ngOnInit(): void {
    this.displayedColumns = this.imageSrcExtractor ? [ ...this.displayedColumns, this.imageColumn ] : this.displayedColumns;
    this.displayedColumns = [
      ...this.displayedColumns,
      ...this.columnDetails.map(details => details.field)
    ];
    this.displayedColumns = this.canRemoveEntity ? [ ...this.displayedColumns, this.removeColumn ] : this.displayedColumns;

    this.handlePageRequests();
  }

  calculatePosition(currentIndex: number): number {
    return currentIndex + 1 + (this.currentDisplayedPage - 1) * Number(this.itemsPerPage);
  }

  handlePageRequests(): void {
    this.dataSource$ = combineLatest([this.loadRequestFn$, this.pageChange$, this.entityRemovedTrigger$]).pipe(
      tap(() => this.loading = true),
      switchMap(([requestFn, page]) => requestFn(page).pipe(
        tap((response: Page<T>) => {
          this.totalItems = response.totalElements;
          this.currentDisplayedPage = page;
          this.loading = false;
        }),
        map((response: Page<T>) => response.content)
      ))
    );
  }

  removeEntity($event: any, entity: T): void {
    // Disabling parent link
    $event.stopPropagation();
    $event.preventDefault();

    this.removeRequestFn$.pipe(
      switchMap(removeRequestFn => removeRequestFn(entity))
    ).subscribe(() => this.entityRemovedTrigger$.next(entity));
  }
}
