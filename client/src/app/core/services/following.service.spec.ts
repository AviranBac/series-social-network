import { TestBed } from '@angular/core/testing';

import { FollowService } from './follow.service';

describe('FollowingService', () => {
  let service: FollowService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FollowService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
