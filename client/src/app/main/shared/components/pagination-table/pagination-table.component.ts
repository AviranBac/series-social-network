import {Component, Input, OnInit} from '@angular/core';
import {BehaviorSubject, combineLatest, map, Observable, Subject, switchMap, tap} from "rxjs";
import {Page} from "../../models/page";

export interface ColumnDetails {
  field: string
  label: string
}

@Component({
  selector: 'app-pagination-table',
  templateUrl: './pagination-table.component.html',
  styleUrls: ['./pagination-table.component.less']
})
export class PaginationTableComponent<T> implements OnInit {
  @Input() paginationId: string;
  @Input() itemsPerPage: number;
  @Input() columnDetails: ColumnDetails[];
  @Input() requestFn$: Observable<(page: number) => Observable<Page<T>>>;
  @Input() imageSrcExtractor?: (entity: T) => string;
  @Input() routerLinkExtractor?: (entity: T) => string;

  dataSource$: Observable<T[]>;
  pageChange$: Subject<number> = new BehaviorSubject<number>(1);
  positionColumn = 'position';
  imageColumn = 'image';
  displayedColumns: string[] = [this.positionColumn];
  currentDisplayedPage: number = 1;
  totalItems: number;

  private loading: boolean;

  constructor() { }

  ngOnInit(): void {
    this.displayedColumns = this.imageSrcExtractor ? [ ...this.displayedColumns, this.imageColumn ] : this.displayedColumns;
    this.displayedColumns = [
      ...this.displayedColumns,
      ...this.columnDetails.map(details => details.field)
    ];

    this.handlePageRequests();
  }

  calculatePosition(currentIndex: number): number {
    return currentIndex + 1 + (this.currentDisplayedPage - 1) * Number(this.itemsPerPage);
  }

  handlePageRequests(): void {
    this.dataSource$ = combineLatest([this.requestFn$, this.pageChange$]).pipe(
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
}
