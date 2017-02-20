import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {RouterModule} from '@angular/router'

//import 'hammerjs'
import { MaterialModule } from '@angular/material';


import { AppComponent }  from './components/app.component';
import { GraphicBrowserComponent }  from './components/graphic-browser.component';
import { NumericalDataComponent }  from './components/numerical-data.component';
import { DashboardComponent }  from './components/dashboard.component';

import { AppRoutingModule }     from './app-routing.module';

@NgModule({
  imports: [
     BrowserModule,
     FormsModule,
     HttpModule,
     ReactiveFormsModule,
     MaterialModule.forRoot(),
     AppRoutingModule
 ],
  declarations: [ AppComponent , GraphicBrowserComponent, NumericalDataComponent, DashboardComponent],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
