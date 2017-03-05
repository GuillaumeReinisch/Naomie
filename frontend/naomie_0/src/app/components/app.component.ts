import { Component , ViewEncapsulation} from '@angular/core';


@Component({
  selector: 'my-app',
  template: `<h1>{{name}}</h1>
             <a routerLink="/dashboard">dashboard</a>
             <a routerLink="/graphics">graphics</a>
             <a routerLink="/scenarios">data</a>
             <router-outlet></router-outlet>
  `,
})
export class AppComponent  { name = 'Naomie'; }
