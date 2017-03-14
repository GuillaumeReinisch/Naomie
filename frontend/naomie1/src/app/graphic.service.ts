import { Injectable }     from '@angular/core';
import {GraphicCollectionForm,Graphic}          from './models/graphic'
import { Headers, Http }  from '@angular/http';

import 'rxjs/add/operator/toPromise';

@Injectable()
export class GraphicsService {

  constructor(private http: Http) { }

  //! Need to allow access using CORS.
  private GraphicsUrl = 'http://localhost:8080/naomie/graphics';  // URL to web api
  private headers      = new Headers({'Content-Type': 'application/json'});

 getGraphics(): Promise<Graphic[]> {
    return this.http.get(this.GraphicsUrl)
              .toPromise()
              .then(response => response.json() as Graphic[] )
              .catch(this.handleError);
 }

 createCollection(form: GraphicCollectionForm): Promise<void> {

   var url = 'http://localhost:8080/naomie/graphics/createCollection'
   return this.http
      .post(url, JSON.stringify(form), {headers: this.headers})
      .toPromise()
      .then(response => null )
      .catch(this.handleError);
 }


 private handleError(error: any): Promise<any> {
   console.error('An error occurred', error); // for demo purposes only
   return Promise.reject(error.message || error);
 }
}
