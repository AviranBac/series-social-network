export interface User {
  userName: string;
  firstName: string;
  lastName: string;
}

export function extractUserRouterLink(user: User) {
  return `/users/${user.userName}`
}
