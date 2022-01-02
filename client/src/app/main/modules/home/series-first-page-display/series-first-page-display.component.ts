import {Component, Input, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {TvSeries} from "../../../shared/models/tv-series";

@Component({
  selector: 'app-series-first-page-display',
  templateUrl: './series-first-page-display.component.html',
  styleUrls: ['./series-first-page-display.component.less']
})
export class SeriesFirstPageDisplayComponent implements OnInit {
  @Input() header: string;
  @Input() subtitle: string;
  @Input() expansionLink: string;
  @Input() loadSeriesFn: () => Observable<TvSeries[]>;

  series$: Observable<TvSeries[]>;

  constructor() { }

  ngOnInit(): void {
    this.series$ = this.loadSeriesFn();
  }
}
