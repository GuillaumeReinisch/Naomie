import { Injectable } from '@angular/core';

import {NumericalData,Scenario, Dataset, NUMERICALDATA}      from './models/numerical-data'
import { Headers, Http } from '@angular/http';


@Injectable()
export class NumericalDataService {

  //! Need to allow access using CORS.
  private DataUrl = 'http://localhost:8080/naomie/scenarios';  // URL to web api
  private headers      = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }

  getScenario(): Promise<Scenario[]> {
    return this.http.get(this.DataUrl)
              .toPromise()
              .then(response => response.json() as Scenario[] )
              .catch(this.handleError);
  }

  deleteScenario(uid : String): Promise<void> {
    return this.http.get(this.DataUrl+"/delete/"+uid).toPromise().then(() => null)
      .catch(this.handleError);
  }

  addScenario(scenario : Scenario): Promise<void> {

    var url = 'http://localhost:8080/naomie/data/createScenario';

    return this.http
      .post(url, JSON.stringify(scenario), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  getDatasets(name:string): Promise<Dataset[]> {

    var url = 'http://localhost:8080/naomie/datasets/'+name;
    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as Dataset[] )
              .catch(this.handleError);
  }

  getNamespaces(): Promise<string[]> {

    var url = 'http://localhost:8080/naomie/namespaces';
    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as string[] )
              .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

}
