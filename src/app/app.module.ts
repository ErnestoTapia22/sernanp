import { DragDropModule } from '@angular/cdk/drag-drop';
import { CdkTableModule } from '@angular/cdk/table';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { APP_INITIALIZER, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { NgxPaginationModule } from 'ngx-pagination';
import { NgxSpinnerModule } from 'ngx-spinner';
import { TreeviewModule } from 'ngx-treeview';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { AdminComponent } from './pages/admin/admin.component';
import { LoginComponent } from './pages/auth/login/login.component';
import { AgreementNewComponent } from './pages/base/agreement/agreement-new/agreement-new.component';
import { AgreementComponent } from './pages/base/agreement/index/agreement.component';
import { HeaderLoginComponent } from './pages/base/header-login/header-login.component';
import { IndexComponent } from './pages/base/index/index.component';
import { BlankLayoutComponent } from './pages/base/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/base/layout/empty-layout/empty-layout.component';
import { FullLayoutComponent } from './pages/base/layout/full-layout/full-layout.component';
import { MonitoringComponent } from './pages/base/monitoring/index/monitoring.component';
import { WorkPlanComponent } from './pages/base/monitoring/work-plan/work-plan.component';
import { NavbarComponent } from './pages/base/navbar/navbar.component';
import { NotFoundComponent } from './pages/base/not-found/not-found.component';
import { PublicComponent } from './pages/base/public/public.component';

import { BaseMapComponent } from './pages/geometry/base-map/base-map.component';
import { BaseWidgetComponent } from './pages/geometry/widgets/base-widget/base-widget.component';
import { CustomWidgetComponent } from './pages/geometry/widgets/custom-widget/custom-widget.component';
import { AnpComponent } from './pages/masterplan/anp/anp.component';
import { MasterPlanComponent } from './pages/masterplan/master-plan/master-plan.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { UserComponent } from './pages/user/user.component';
import { AlertComponent } from './pages/_directives/alert/alert.component';
import { ResizeMapDirective } from './pages/_directives/resize-map.directive';
import { initAppFactory } from './_factory/init.factory';
import { AlertService } from './_services/base/alert.service';
import { BaseService } from './_services/base/base.service';
import { EsriMapService } from './_services/geometry/esri-map.service';
import { LayerService } from './_services/geometry/layer.service';
import { InitService } from './_services/init-service/init.service';

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

    IndexComponent,
    BaseWidgetComponent,
    CustomWidgetComponent,
    AgreementComponent,
    ResizeMapDirective,
    AlertComponent,
    MonitoringComponent,
    ReportsComponent,
    UserComponent,

    AdminComponent,
    AnpComponent,
    MasterPlanComponent,
    AgreementNewComponent,

    NotFoundComponent,
    PublicComponent,
    WorkPlanComponent,
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
    NgMultiSelectDropDownModule.forRoot(),
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
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
  exports: [BaseWidgetComponent, DragDropModule, AlertComponent],
})
export class AppModule {}
