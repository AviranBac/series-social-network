import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SeriesFirstPageDisplayComponent} from './series-first-page-display.component';

describe('SeriesFirstPageDisplayComponent', () => {
  let component: SeriesFirstPageDisplayComponent;
  let fixture: ComponentFixture<SeriesFirstPageDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SeriesFirstPageDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SeriesFirstPageDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
