import {ComponentFixture, TestBed} from '@angular/core/testing';

import {WatchlistContainerComponent} from './watchlist-container.component';

describe('WatchlistComponent', () => {
  let component: WatchlistContainerComponent;
  let fixture: ComponentFixture<WatchlistContainerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchlistContainerComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WatchlistContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
