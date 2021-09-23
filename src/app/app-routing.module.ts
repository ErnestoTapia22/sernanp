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
import { MonitoringComponent } from './pages/base/monitoring/index/monitoring.component';
import { DetailComponent } from './pages/base/monitoring/detail/detail.component';
import { ReportsComponent } from './pages/reports/reports.component';
import { UserComponent } from './pages/user/user.component';
import { TestComponent } from './pages/base/test/test.component';
import { AdminComponent } from './pages/admin/admin.component';
import { AnpComponent } from './pages/masterplan/anp/anp.component';
import { MasterPlanComponent } from './pages/masterplan/master-plan/master-plan.component';
import { NotFoundComponent } from './pages/base/not-found/not-found.component';
import { AuthGuard } from './helpers/auth.guard';
import { Role } from './_models/auth/role';
import { PublicComponent } from './pages/base/public/public.component';
import { WorkPlanComponent } from './pages/base/monitoring/work-plan/work-plan.component';

const routes: Routes = [
  { path: '', redirectTo: 'default', pathMatch: 'full' },
  {
    path: 'default',
    component: BlankLayoutComponent,
    children: [
      { path: '', redirectTo: 'login', pathMatch: 'full' },
      { path: 'login', component: LoginComponent },
    ],
  },
  {
    path: 'authentication/:token',
    component: PublicComponent,
  },
  {
    path: 'map',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      // { path: 'visor', component: BaseMapComponent },
      { path: 'index', component: IndexComponent },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/map' },
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
        component: AgreementNewComponent,
      },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/agreement/index' },
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
      {
        path: 'work-plan/:id',
        component: WorkPlanComponent,
      },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/monitoring/index' },
  },
  {
    path: 'reports',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: ReportsComponent },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/reports/index' },
  },
  {
    path: 'user',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: UserComponent },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/user/index' },
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
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/admin/index' },
  },
  {
    path: 'anp',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: AnpComponent },
      {
        path: 'masterplan/:id/:withMasterPlan',
        component: MasterPlanComponent,
      },
    ],
    canActivate: [AuthGuard],
    data: { roles: ['Administrador SIMRAC'], module: '/anp/index' },
  },
  { path: 'not-found', component: NotFoundComponent },
  { path: '**', redirectTo: 'not-found' },
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
})
export class AppRoutingModule {}
