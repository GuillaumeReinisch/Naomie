import { Component, OnInit } from '@angular/core';

import {UsersService} from '../users.service';
import { UserForm }   from '../models/user'

@Component({
  selector: 'app-login',
  providers: [UsersService],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  constructor(private usersService: UsersService) { }

  ngOnInit() {
  }

  login( form:any ) {
        this.usersService.login(new UserForm(form.email,form.password));
    }

}
