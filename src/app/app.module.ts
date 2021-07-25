import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgxSpinnerModule } from 'ngx-spinner';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FullLayoutComponent } from './layout/full-layout/full-layout.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './login/login.component';
import { BlankLayoutComponent } from './layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './layout/empty-layout/empty-layout.component';
import { AlertDirective } from './_directives/alert.directive';
import { NavbarComponent } from './navbar/navbar.component';
import { HeaderLoginComponent } from './header-login/header-login.component';

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
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    NgbModule,
    NgxSpinnerModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
