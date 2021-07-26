import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxSpinnerModule } from 'ngx-spinner';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FullLayoutComponent } from './pages/layout/full-layout/full-layout.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './pages/login/login.component';
import { BlankLayoutComponent } from './pages/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/layout/empty-layout/empty-layout.component';
import { AlertDirective } from './pages/_directives/alert.directive';
import { NavbarComponent } from './pages/navbar/navbar.component';
import { HeaderLoginComponent } from './pages/header-login/header-login.component';
import { BaseMapComponent } from './base-map/base-map.component';
import { TableComponent } from './pages/table/table.component';
import { EsriMapService } from './_services/esri-map.service';
import { CdkTableModule } from '@angular/cdk/table';

@NgModule({
  declarations: [
    AppComponent,
    FullLayoutComponent,
    LoginComponent,
    BlankLayoutComponent,
    EmptyLayoutComponent,
    AlertDirective,
    NavbarComponent,
    HeaderLoginComponent,
    BaseMapComponent,
    TableComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    NgbModule,
    NgxSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CdkTableModule,
  ],
  providers: [EsriMapService],
  bootstrap: [AppComponent],
})
export class AppModule {}
