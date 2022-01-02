import {ComponentFixture, TestBed} from '@angular/core/testing';

import {YourFollowingComponent} from './your-following.component';

describe('FollowingComponent', () => {
  let component: YourFollowingComponent;
  let fixture: ComponentFixture<YourFollowingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YourFollowingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(YourFollowingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
