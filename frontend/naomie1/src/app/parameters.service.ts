import { Injectable } from '@angular/core';
import { Collection, CollectionForm}      from './models/collection'
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
