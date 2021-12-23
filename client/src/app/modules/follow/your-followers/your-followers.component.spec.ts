import {ComponentFixture, TestBed} from '@angular/core/testing';

import {YourFollowersComponent} from './your-followers.component';

describe('YourFollowersComponent', () => {
  let component: YourFollowersComponent;
  let fixture: ComponentFixture<YourFollowersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YourFollowersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(YourFollowersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
