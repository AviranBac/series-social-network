import {ComponentFixture, TestBed} from '@angular/core/testing';

import {WatchlistSeriesDetailsComponent} from './watchlist-series-details.component';

describe('WatchlistSeriesDetailsComponent', () => {
  let component: WatchlistSeriesDetailsComponent;
  let fixture: ComponentFixture<WatchlistSeriesDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchlistSeriesDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchlistSeriesDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
