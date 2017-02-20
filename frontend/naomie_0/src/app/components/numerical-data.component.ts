import { Component }            from '@angular/core';
import { NumericalData}         from '../models/numerical-data'
import { OnInit }               from '@angular/core';

import { GraphicService }       from '../services/graphic.service';
import { NumericalDataService } from '../services/numerical-data.service';

@Component({
  selector: 'numerical-data',
  providers:[NumericalDataService],
  template: `<h2>numerical data</h2>`,
  styles: []
})


export class NumericalDataComponent implements OnInit {

  numericalData: NumericalData;

  constructor(private dataService: NumericalDataService) { }

  ngOnInit(): void {
    this.dataService.getNumericalData().then( data => this.numericalData = data);
   }

}
