import {Component, Input, OnInit} from '@angular/core';
import {map, Observable, tap} from "rxjs";
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
  @Input() requestFn: (page: number) => Observable<Page<T>>;

  dataSource$: Observable<T[]>;
  positionColumn = 'position';
  displayedColumns: string[] = [this.positionColumn];
  currentDisplayedPage: number = 1;
  totalItems: number;
  loading: boolean;

  constructor() { }

  ngOnInit(): void {
    this.displayedColumns = [
      ...this.displayedColumns,
      ...this.columnDetails.map(details => details.field)
    ];
    this.getPage(1);
  }

  calculatePosition(currentIndex: number): number {
    return currentIndex + 1 + (this.currentDisplayedPage - 1) * Number(this.itemsPerPage);
  }

  getPage(page: number): void {
    this.loading = true;
    this.dataSource$ = this.requestFn(page).pipe(
      tap((response: Page<T>) => {
        this.totalItems = response.totalElements;
        this.currentDisplayedPage = page;
        this.loading = false;
      }),
      map(response => response.content)
    );
  }
}
