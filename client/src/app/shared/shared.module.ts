import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {RouterModule} from "@angular/router";
import {PaginationTableComponent} from './components/pagination-table/pagination-table.component';
import {MatTableModule} from "@angular/material/table";
import {NgxPaginationModule} from "ngx-pagination";

@NgModule({
  declarations: [
    NavBarComponent,
    PaginationTableComponent
  ],
  exports: [
    NavBarComponent,
    PaginationTableComponent
  ],
  imports: [
    CommonModule,
    MatToolbarModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    RouterModule,
    MatTableModule,
    NgxPaginationModule
  ]
})
export class SharedModule { }
