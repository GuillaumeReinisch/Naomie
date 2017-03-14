import { Injectable } from '@angular/core';
import { Project, ProjectForm}      from './models/project'
import { Headers, Http } from '@angular/http';
import { User } from './models/user'
import { Router } from '@angular/router';
import 'rxjs/add/operator/toPromise';


@Injectable()
export class ProjectsService {

  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http, private _router: Router ) { }

  getProjects( userName: string): Promise<Project[]> {

    var url = 'http://localhost:8080/naomie/projects/'+userName;

    return this.http.get(url)
              .toPromise()
              .then(response => response.json() as Project[] )
              .catch(this.handleError);
  }

  createProject( project : ProjectForm ) :  void {

    var url = 'http://localhost:8080/naomie/projects/createProject';

    this.http
      .post(url, JSON.stringify(project), {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
