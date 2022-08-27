import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './site/header/header.component';
import { LoginComponent } from './site/login/login.component';
import { SignupComponent } from './site/signup/signup.component';
import { AgGridModule } from 'ag-grid-angular';
import { SearchCompanyComponent } from './company/search-company/search-company.component';
import { AddCompanyComponent } from './company/add-company/add-company.component';
import { CellLinkComponent } from './common/cell-link/cell-link.component';
import { DetailCompanyComponent } from './company/detail-company/detail-company.component';
import { DatePipe } from '@angular/common';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ErrorhandlerInterceptor } from './errorhandler.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    SignupComponent,
    SearchCompanyComponent,
    AddCompanyComponent,
    CellLinkComponent,
    DetailCompanyComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    AgGridModule,
    HttpClientModule
  ],
  providers: [DatePipe,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorhandlerInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
