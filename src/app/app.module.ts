import { NgModule, APP_INITIALIZER } from '@angular/core';
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
import { AgreementComponent } from './pages/base/agreement/index/agreement.component';
import { ResizeMapDirective } from './pages/_directives/resize-map.directive';
import { AlertComponent } from './pages/_directives/alert/alert.component';
import { BaseService } from './_services/base/base.service';
import { MonitoringComponent } from './pages/base/monitoring/index/monitoring.component';
import { ReportsComponent } from './pages/base/reports/reports.component';
import { UserComponent } from './pages/base/user/user.component';
import { DetailComponent } from './pages/base/monitoring/detail/detail.component';
import { TestComponent } from './pages/base/test/test.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { AdminComponent } from './pages/base/admin/admin.component';
import { initAppFactory } from './_factory/init.factory';
import { AlertService } from './_services/base/alert.service';
import { ApiBaseService } from './_services/base/api-base.service';
import { InitService } from './_services/init-service/init.service';
import { NgSelectModule } from '@ng-select/ng-select';
import { AnpComponent } from './pages/base/anp/anp.component';
import { MasterPlanComponent } from './pages/base/master-plan/master-plan.component';
import { AgreementNewComponent } from './pages/base/agreement/agreement-new/agreement-new.component';
import { AgreementDetailComponent } from './pages/base/agreement/agreement-detail/agreement-detail.component';
import { NotFoundComponent } from './pages/base/not-found/not-found.component';
import { PublicComponent } from './pages/base/public/public.component';

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
    AdminComponent,
    AnpComponent,
    MasterPlanComponent,
    AgreementNewComponent,
    AgreementDetailComponent,
    NotFoundComponent,
    PublicComponent,
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
    NgxPaginationModule,
    NgSelectModule,
  ],
  providers: [
    EsriMapService,
    LayerService,
    BaseService,
    {
      provide: APP_INITIALIZER,
      useFactory: initAppFactory,
      deps: [InitService, AlertService],
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  exports: [BaseWidgetComponent, DragDropModule, AlertComponent],
})
export class AppModule {}
