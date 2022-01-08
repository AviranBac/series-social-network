import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SearchSeriesComponent} from './search-series.component';

describe('SearchSeriesComponent', () => {
  let component: SearchSeriesComponent;
  let fixture: ComponentFixture<SearchSeriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SearchSeriesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchSeriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
