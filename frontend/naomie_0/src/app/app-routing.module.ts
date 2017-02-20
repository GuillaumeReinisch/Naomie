import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { GraphicBrowserComponent }  from './components/graphic-browser.component';
import { NumericalDataComponent }  from './components/numerical-data.component';
import { DashboardComponent }  from './components/dashboard.component';



const routes: Routes = [
  { path: 'graphics', component: GraphicBrowserComponent},
  { path: 'data', component: NumericalDataComponent},
  { path: 'dashboard', component: DashboardComponent},
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
