import { Component  , ViewEncapsulation, OnInit} from '@angular/core';
import { MdButton } from '@angular2-material/button';
import { MdProgressCircle } from '@angular/material';
import {MdSidenav, MdSidenavContainer} from '@angular/material';
import {MdSidenavModule} from "@angular/material";
import {MdToolbar} from "@angular/material";
import { Router }               from '@angular/router';
import { User }                 from './models/user';
import { LocalStorageService }  from 'angular-2-local-storage';
import {UsersService} from './users.service';

@Component({
  selector: 'app-root',
  providers: [UsersService],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit  {

  name = 'Naomie';
  user : string;

  constructor(public usersService: UsersService,
              private localStorageService: LocalStorageService,
              private _router: Router) { }

  ngOnInit(): void {
    this.user = this.usersService.connected;
  }

  logout() : void {
    this.usersService.logout();
    this.user = null;
  }
}
