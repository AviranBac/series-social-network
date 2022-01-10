import {Component, OnInit} from '@angular/core';
import {RequestedSeriesFilter, TvSeriesService} from "../../../../core/services/tv-series.service";
import {
  combineLatest,
  debounceTime,
  map,
  Observable,
  shareReplay,
  Subject,
  switchMap,
  take,
  takeUntil,
  tap
} from "rxjs";
import {getStatusValue, SeriesStatus} from "../../../shared/models/series-status";
import {FormArray, FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {TvGenre} from "../../../shared/models/genre";
import {extractSeriesImageSrc, extractSeriesRouterLink, TvSeries} from "../../../shared/models/tv-series";
import {
  ColumnDetails,
  seriesColumnDetails
} from "../../../shared/components/pagination-table/pagination-table.component";
import {Page} from "../../../shared/models/page";
import {Destroyable} from "../../../shared/utils/destroyable.utils";

@Component({
  selector: 'app-search-series',
  templateUrl: './search-series.component.html',
  styleUrls: ['./search-series.component.less']
})
export class SearchSeriesComponent extends Destroyable implements OnInit {
  form: FormGroup;
  seriesStatuses$: Observable<SeriesStatus[]>;
  genres$: Observable<TvGenre[]>;
  seriesImageSrcExtractor: (tvSeries: TvSeries) => string = extractSeriesImageSrc;
  seriesRouterLinkExtractor: (tvSeries: TvSeries) => string = extractSeriesRouterLink;
  seriesColumnDetails: ColumnDetails[] = seriesColumnDetails;
  requestFn$: Observable<(page: number) => Observable<Page<TvSeries>>>;

  private seriesFilters$: Subject<RequestedSeriesFilter> = new Subject<RequestedSeriesFilter>();

  constructor(private tvSeriesService: TvSeriesService,
              private formBuilder: FormBuilder) {
    super();
    this.form = this.formBuilder.group({
      name: new FormControl(''),
      seriesStatuses: new FormArray([]),
      genres: new FormArray([])
    })
  }

  ngOnInit(): void {
    const allFilterOptions$ = this.tvSeriesService.loadSeriesFilterOptions().pipe(
      shareReplay()
    );

    this.seriesStatuses$ = allFilterOptions$.pipe(
      map(allOptions => allOptions.seriesStatuses),
      tap(statuses => statuses.forEach(() => (this.form.controls['seriesStatuses'] as FormArray).push(new FormControl(true)))),
      shareReplay()
    );

    this.genres$ = allFilterOptions$.pipe(
      map(allOptions => allOptions.genres),
      tap(genres => genres.forEach(() => (this.form.controls['genres'] as FormArray).push(new FormControl(true)))),
      shareReplay()
    );

    // Listen to form changes after first initialization
    combineLatest([this.seriesStatuses$, this.genres$]).pipe(
      take(1),
      tap(() => this.updateRequestedSeriesFilter(this.form.value)),
      switchMap(() => this.form.valueChanges),
      debounceTime(500),
      takeUntil(this.destroy$)
    ).subscribe(changes => this.updateRequestedSeriesFilter(changes));

    this.requestFn$ = this.seriesFilters$.pipe(
      map((requestedSeriesFilter: RequestedSeriesFilter) => (page: number) => this.tvSeriesService.loadSeriesByFilter(page, requestedSeriesFilter)),
      takeUntil(this.destroy$)
    );
  }

  updateRequestedSeriesFilter(formValues: any): void {
    combineLatest([this.seriesStatuses$, this.genres$]).pipe(
      take(1),
      map(([seriesStatuses, genres]) => {
        const requestedSeriesStatuses: SeriesStatus[] = (formValues.seriesStatuses as boolean[]).reduce((acc, curr, index) =>
          curr ? [...acc, ...[seriesStatuses[index]]] : [...acc]
        , [] as SeriesStatus[]);
        const requestedGenreIds: number[] = (formValues.genres as boolean[]).reduce((acc, curr, index) =>
          curr ? [...acc, ...[genres[index].id]] : [...acc]
        , [] as number[]);

        return {
          name: formValues.name,
          seriesStatuses: requestedSeriesStatuses,
          genreIds: requestedGenreIds
        }
      })
    ).subscribe((requestedSeriesFilter: RequestedSeriesFilter) => this.seriesFilters$.next(requestedSeriesFilter));
  }

  getSeriesStatusValue(seriesStatus: SeriesStatus) {
    return getStatusValue(seriesStatus);
  }
}
