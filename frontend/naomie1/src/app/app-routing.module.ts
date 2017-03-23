import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { DashboardComponent }       from './dashboard/dashboard.component';
import { LoginComponent }           from './login/login.component';
import { GraphicBrowserComponent }  from './graphic-browser/graphic-browser.component';
import { ParametersBrowserComponent }  from './parameters-browser/parameters-browser.component';
/*
import { GraphicBrowserComponent }  from './components/graphic-browser.component';
import { GraphCreatorComponent }    from './components/graph-creator.component';
import { NumericalDataComponent }   from './components/numerical-data.component';
import { ScenarioViewComponent }    from './components/scenario-view.component';
import { LoginComponent }           from './user/login/login.component';
*/



const routes: Routes = [
  { path: 'login',          component: LoginComponent},
  { path: 'graphics',       component: GraphicBrowserComponent},
  { path: 'parameters',     component: ParametersBrowserComponent},
  //{ path: 'scenarios',      component: NumericalDataComponent},
  { path: 'dashboard',      component: DashboardComponent},
  //{ path: 'scenario/:id',   component: ScenarioViewComponent},
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})

export class AppRoutingModule {}
