import {NgModule} from '@angular/core';
import {AuthenticationGuard} from "./auth/authentication.guard";

@NgModule({
  imports: [],
  providers: [
    AuthenticationGuard
  ],
})
export class CoreModule { }
