import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { BlankLayoutComponent } from './pages/base/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/base/layout/empty-layout/empty-layout.component';
import { FullLayoutComponent } from './pages/base/layout/full-layout/full-layout.component';
import { BaseMapComponent } from './pages/geometry/base-map/base-map.component';
import { IndexComponent } from './pages/base/index/index.component';
import { AgreementComponent } from './pages/base/agreement/index/agreement.component';
import { AgreementNewComponent } from './pages/base/agreement/agreement-new/agreement-new.component';
import { AgreementDetailComponent } from './pages/base/agreement/agreement-detail/agreement-detail.component';
import { MonitoringComponent } from './pages/base/monitoring/index/monitoring.component';
import { DetailComponent } from './pages/base/monitoring/detail/detail.component';
import { ReportsComponent } from './pages/base/reports/reports.component';
import { UserComponent } from './pages/base/user/user.component';
import { TestComponent } from './pages/base/test/test.component';
import { AdminComponent } from './pages/base/admin/admin.component';
import { AnpComponent } from './pages/base/anp/anp.component';
import { MasterPlanComponent } from './pages/base/master-plan/master-plan.component';

const routes: Routes = [
  { path: '', redirectTo: 'authentication', pathMatch: 'full' },
  {
    path: 'authentication',
    component: BlankLayoutComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
    ],
  },
  {
    path: 'map',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      // { path: 'visor', component: BaseMapComponent },
      { path: 'index', component: IndexComponent },
    ],
  },
  {
    path: 'agreement',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: AgreementComponent },
      {
        path: 'new',
        component: AgreementNewComponent,
      },
      {
        path: 'detail/:id',
        component: AgreementDetailComponent,
      },
    ],
  },
  {
    path: 'monitoring',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: MonitoringComponent },
      {
        path: 'detail/:id',
        component: DetailComponent,
      },
    ],
  },
  {
    path: 'reports',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: ReportsComponent },
    ],
  },
  {
    path: 'user',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: UserComponent },
    ],
  },
  {
    path: 'admin',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      {
        path: 'index',
        component: AdminComponent,
      },
    ],
  },
  {
    path: 'anp',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: AnpComponent },
      { path: 'masterplan', component: MasterPlanComponent },
    ],
  },
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
})
export class AppRoutingModule {}
