import { Injectable } from '@angular/core';
import { Project}      from './models/project'
import { Headers, Http } from '@angular/http';

import { Router } from '@angular/router';
import { User , UserForm}   from './models/user'

import 'rxjs/add/operator/toPromise';

import { LocalStorageService } from 'angular-2-local-storage';


@Injectable()
export class UsersService {

  public connected : string = "nobody";

  private serverUrl = 'http://localhost:8080/naomie';  // URL to web api
  private user : User

  private loginRoute:string  = this.serverUrl+'/users/login';
  private logoutRoute:string = this.serverUrl+'/users/logout';
  private signupRoute:string = this.serverUrl+'/users/signup';
  private signUpSuccessRoute:string[] = ['/dashboard'];
  private loginSuccessRoute:string[] = ['/dashboard'];

  private headers      = new Headers({'Content-Type': 'application/json'});

  constructor(
        private http: Http,
        private _router: Router,
        private localStorageService: LocalStorageService){
        this.connected = <string> this.localStorageService.get('user');
      }

      private static handleError(error: any): Promise<any> {
          console.error('An error occurred', error); // for demo purposes only
          return Promise.reject(error.message || error);
      }

      login(user: UserForm):Promise<any> {

        //if (this.isAuthenticated())  {
        //  return this._router.navigate(this.loginSuccessRoute);
        //}
        return this.http.post(this.loginRoute, JSON.stringify(user), {headers: this.headers} )
          .toPromise()
          .then((response) => {
              this.user = response['_body'];
              console.log("user=", this.user)
              this.localStorageService.add('user', "guigui");
              this._router.navigate(this.loginSuccessRoute);
            }).catch(UsersService.handleError);
      }

      logout() : void {
          this.localStorageService.remove('user');
      }

      signup(user:User):Promise<any> {
          if (this.isAuthenticated())  {
              return this._router.navigate(this.signUpSuccessRoute);
          }
          return this.http.post(this.signupRoute, user)
              .toPromise()
              .then(() => {
                  this._router.navigate(this.signUpSuccessRoute);
              }).catch(UsersService.handleError);
        }

        isAuthenticated():Boolean {
            return this.user !== null;
        }

        getUser(): User {
            return (this.user) ?  this.user : null;
        }
}
