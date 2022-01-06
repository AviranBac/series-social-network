import {ComponentFixture, TestBed} from '@angular/core/testing';

import {WatchlistRibbonComponent} from './watchlist-ribbon.component';

describe('WatchlistRibbonComponent', () => {
  let component: WatchlistRibbonComponent;
  let fixture: ComponentFixture<WatchlistRibbonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchlistRibbonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchlistRibbonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
