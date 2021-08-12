import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { BlankLayoutComponent } from './pages/base/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/base/layout/empty-layout/empty-layout.component';
import { FullLayoutComponent } from './pages/base/layout/full-layout/full-layout.component';
import { BaseMapComponent } from './pages/geometry/base-map/base-map.component';
import { IndexComponent } from './pages/base/index/index.component';
import { ConsultationsComponent } from './pages/base/consultations/consultations.component';

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
      { path: '', redirectTo: 'visor', pathMatch: 'full' },
      { path: 'visor', component: BaseMapComponent },
      { path: 'index', component: IndexComponent },
    ],
  },
  {
    path: 'consultations',
    component: FullLayoutComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'index', component: ConsultationsComponent },
    ],
  },
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
})
export class AppRoutingModule {}
