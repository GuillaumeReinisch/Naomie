import { Component,Input}            from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NumericalData, Scenario, Dataset }         from '../models/numerical-data'
import { OnInit }               from '@angular/core';

import { NumericalDataService} from '../services/numerical-data.service';


@Component({
  selector: 'scenario-view',
  providers:[NumericalDataService],
  template: `<h2>{{scenario}} view</h2>
  <ul class="dataset">
     <li *ngFor="let dataset of datasets"
       [class.selected]="dataset === selectedDataset"
       (click)="onSelect(dataset)">
       <span class="badge">{{dataset.name}}</span>

       <table style="width:100%">
          <tr>
            <th> parameters </th>
            <th *ngFor="let hheader of getHHeaders(dataset)"> {{hheader}} </th>
          </tr>
          <tr *ngFor="let line of dataset.vheaders; let i = index;">
            <th> {{line}} </th>
            <th *ngFor="let value of getValues(dataset,i)"> {{value}} </th>
          </tr>
      </table>
     </li>
   </ul>
  `
})


export class ScenarioViewComponent implements OnInit {

  scenario : string
  datasets : Dataset[]
  selectedDataset : Dataset
  private sub: any;

  constructor(private dataService: NumericalDataService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    /*
    console.log("ngOnInit")
    var datasetsTmp :Dataset[] ;
    var that = this;
    this.dataService.getDatasets(this.scenario.name).then( datasets =>  this.datasets = datasets)
    */
    this.sub = this.route.params.subscribe(params => {
      this.scenario = params['id']
      this.dataService.getDatasets(this.scenario).then( datasets =>  this.datasets = datasets)
            // In a real app: dispatch action to load the details here.
   });

  }
  onSelect(dataset : Dataset) : void {

       this.selectedDataset = dataset;
       console.log(dataset)
     }

  getHHeaders(dataset : Dataset) : string[] {
    return Object.keys(dataset.columns)
  }
  getValues(dataset : Dataset, lineIndex: number ) : number[] {

    var values = new Array<number>()
    Object.keys(dataset.columns).forEach( header => values.push(dataset.columns[header][lineIndex]))
    return values
  }

}
