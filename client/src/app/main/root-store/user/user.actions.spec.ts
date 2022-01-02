import * as fromUser from './user.actions';

describe('loadUsers', () => {
  it('should return an action', () => {
    expect(fromUser.loadActiveUser().type).toBe('[User] Load Users');
  });
});
