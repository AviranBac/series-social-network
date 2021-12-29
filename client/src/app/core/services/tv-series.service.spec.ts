import {TestBed} from '@angular/core/testing';

import {TvSeriesService} from './tv-series.service';

describe('TvSeriesService', () => {
  let service: TvSeriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TvSeriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
