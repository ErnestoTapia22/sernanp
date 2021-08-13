import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NgxSpinnerModule } from 'ngx-spinner';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FullLayoutComponent } from './pages/base/layout/full-layout/full-layout.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './pages/auth/login/login.component';
import { BlankLayoutComponent } from './pages/base/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/base/layout/empty-layout/empty-layout.component';
import { NavbarComponent } from './pages/base/navbar/navbar.component';
import { HeaderLoginComponent } from './pages/base/header-login/header-login.component';
import { BaseMapComponent } from './pages/geometry/base-map/base-map.component';
import { TableComponent } from './pages/base/table/table.component';
import { EsriMapService } from './_services/geometry/esri-map.service';
import { CdkTableModule } from '@angular/cdk/table';
import { IndexComponent } from './pages/base/index/index.component';
import { BaseWidgetComponent } from './pages/geometry/widgets/base-widget/base-widget.component';
import { CustomWidgetComponent } from './pages/geometry/widgets/custom-widget/custom-widget.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { TreeviewModule } from 'ngx-treeview';
import { LayerService } from './_services/geometry/layer.service';
import { AgreementComponent } from './pages/base/agreement/agreement.component';
import { ResizeMapDirective } from './pages/_directives/resize-map.directive';
import { AlertComponent } from './pages/_directives/alert/alert.component';
import { BaseService } from './_services/base/base.service';
import { MonitoringComponent } from './pages/base/monitoring/index/monitoring.component';
import { ReportsComponent } from './pages/base/reports/reports.component';
import { UserComponent } from './pages/base/user/user.component';
import { DetailComponent } from './pages/base/monitoring/detail/detail.component';
import { TestComponent } from './pages/base/test/test.component';

@NgModule({
  declarations: [
    AppComponent,
    FullLayoutComponent,
    LoginComponent,
    BlankLayoutComponent,
    EmptyLayoutComponent,
    NavbarComponent,
    HeaderLoginComponent,
    BaseMapComponent,
    TableComponent,
    IndexComponent,
    BaseWidgetComponent,
    CustomWidgetComponent,
    AgreementComponent,
    ResizeMapDirective,
    AlertComponent,
    MonitoringComponent,
    ReportsComponent,
    UserComponent,
    DetailComponent,
    TestComponent,
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
    DragDropModule,
    TreeviewModule.forRoot(),
  ],
  providers: [EsriMapService, LayerService, BaseService],
  bootstrap: [AppComponent],
  exports: [BaseWidgetComponent, DragDropModule, AlertComponent],
})
export class AppModule {}