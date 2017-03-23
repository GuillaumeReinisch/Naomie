import { Injectable } from '@angular/core';
import { Collection, CollectionForm}      from './models/collection'
import { Parameter, ParameterForm,ParameterSummary}      from './models/parameter'
import { Headers, Http } from '@angular/http';
import { User } from './models/user'
import { Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class ParametersService {

  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http, private _router: Router ) { }

  getCollections( userName: string): Promise<Collection[]> {

    var url = 'http://localhost:8080/naomie/collections/'+userName;

    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as Collection[] )
              .catch(this.handleError);
  }

  getVariables( ): Promise<string[]> {

    var url = 'http://localhost:8080/naomie/data/variables';

    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as string[] )
              .catch(this.handleError);
  }

  getParametersSummary(collection : string ): Promise<ParameterSummary[]> {

    var url = 'http://localhost:8080/naomie/data/parametersSummary/'+collection;

    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as ParameterSummary[] )
              .catch(this.handleError);
  }

  getParameters( ): Promise<Parameter[]> {

    var url = 'http://localhost:8080/naomie/data/parameters';

    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as Parameter[] )
              .catch(this.handleError);
  }

  createParameter( parameter : ParameterForm ) :  void {

    var url = 'http://localhost:8080/naomie/data/createParameter';
    console.log(parameter)
    this.http
      .post(url, JSON.stringify(parameter), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  createCollection( collection : CollectionForm ) :  void {

    var url = 'http://localhost:8080/naomie/createCollection';

    this.http
      .post(url, JSON.stringify(collection), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
