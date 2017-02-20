import { Component }            from '@angular/core';
import { OnInit }               from '@angular/core';

import { GraphicService }       from '../services/graphic.service';
import { NumericalDataService } from '../services/numerical-data.service';

@Component({
  selector: 'dashboard',
  providers:[NumericalDataService,GraphicService],
  template: `<h2>Dashboard</h2>`,
  styles: []
})


export class DashboardComponent implements OnInit {

  constructor(private dataService: NumericalDataService, private graphicService : GraphicService) { }

  ngOnInit(): void { }

}
