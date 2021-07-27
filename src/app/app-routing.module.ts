import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { BlankLayoutComponent } from './pages/layout/blank-layout/blank-layout.component';
import { EmptyLayoutComponent } from './pages/layout/empty-layout/empty-layout.component';
import { FullLayoutComponent } from './pages/layout/full-layout/full-layout.component';
import { BaseMapComponent } from './base-map/base-map.component';
import { IndexComponent } from './pages/index/index.component';

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
];
@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
})
export class AppRoutingModule {}
