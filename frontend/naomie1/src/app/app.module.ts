import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule} from '@angular/forms';
import { HttpModule } from '@angular/http';
import {RouterModule} from '@angular/router';

import { AppComponent } from './app.component';

import { MaterialModule } from '@angular/material';

import { LocalStorageModule }         from 'angular-2-local-storage';
import { DashboardComponent,DialogNewProject, DialogScenarios }        from './dashboard/dashboard.component';

import { GraphicBrowserComponent }    from './graphic-browser/graphic-browser.component';
import { NewCollectionDialog }        from './graphic-browser/new-collection.dialog';
import { NewParameterDialog }        from './parameters-browser/new-parameter.dialog';

import { AppRoutingModule }           from './app-routing.module';
import { LoginComponent }             from './login/login.component';
import { ScatterplotComponent } from './scatterplot/scatterplot.component';
import { ParametersBrowserComponent } from './parameters-browser/parameters-browser.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    GraphicBrowserComponent,
    LoginComponent,
    DialogNewProject,
    DialogScenarios,
    NewCollectionDialog,
    NewParameterDialog,
    ScatterplotComponent,
    ParametersBrowserComponent
  ],
  entryComponents: [DialogNewProject,DialogScenarios,NewCollectionDialog, NewParameterDialog],
  imports: [
    LocalStorageModule.withConfig({
            prefix: 'naomie',
            storageType: 'localStorage'
        }),
    BrowserModule,
    FormsModule,
    MaterialModule.forRoot(),
    HttpModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
