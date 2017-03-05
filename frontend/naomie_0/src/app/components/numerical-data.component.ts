import { Component }            from '@angular/core';
import { NumericalData, Scenario, Dataset }         from '../models/numerical-data'
import { OnInit }               from '@angular/core';

import { GraphicService }       from '../services/graphic.service';
import { NumericalDataService} from '../services/numerical-data.service';

@Component({
  selector: 'numerical-data',
  providers:[NumericalDataService],
  template: `<h2>Scenario</h2>
  <div>
  <label>Scenario name:</label> <input #scenarioName />
  <button (click)="add(scenarioName.value); scenarioName.value=''">
    Add
  </button>
</div>

   <ul class="scenario">
      <li *ngFor="let scenario of scenarios"
        [class.selected]="scenario === selectedScenario"
        (click)="onSelect(scenario)">

        <a [routerLink]="[ '/scenario', scenario.name ]">{{scenario.name}}</a>

        <ul class="datasets" *ngIf ="scenario === selectedScenario" >
           <li *ngFor="let dataset of datasets"
             [class.selected]="dataset === selectedDataset"
             (click)="onSelectDataset(dataset)">
             <span class="badge">{{dataset.name}}</span>
           </li>
         </ul>

        <button class="delete" (click)="delete(scenario); $event.stopPropagation()">x</button>
      </li>
    </ul>

    <router-outlet></router-outlet>
    `,
    styles: [`
      .selected {
        background-color: #CFD8DC !important;
        color: white;
      }
      button.delete {
        float:right;
        margin-top: 2px;
        margin-right: .8em;
        background-color: gray !important;
        color:white;
      }
      .scenario {
        margin: 0 0 2em 0;
        list-style-type: none;
        padding: 0;
        width: 30em;
      }
      .scenario li {
        cursor: pointer;
        position: relative;
        left: 0;
        background-color: #EEE;
        margin: .5em;
        padding: .3em 0;
        height: 1.6em;
        border-radius: 4px;
      }
      .scenario li.selected:hover {
        background-color: #BBD8DC !important;
        color: white;
      }
      .scenario li:hover {
        color: #607D8B;
        background-color: #DDD;
        left: .1em;
      }
      .scenario .text {
        position: relative;
        top: -3px;
      }
      .scenario .badge {
        display: inline-block;
        font-size: small;
        color: white;
        padding: 0.8em 0.7em 0 0.7em;
        background-color: #607D8B;
        line-height: 1em;
        position: relative;
        left: -1px;
        top: -4px;
        height: 1.8em;
        margin-right: .8em;
        border-radius: 4px 0 0 4px;
      }
    `]
})


export class NumericalDataComponent implements OnInit {

  numericalData: NumericalData;

  scenarios: Scenario[];

  selectedScenario : Scenario;

  datasets: Dataset[];

  selectedDataset: Dataset;

  constructor(private dataService: NumericalDataService) { }

  ngOnInit(): void {
      this.dataService.getScenario().then( data => this.scenarios = data);
   }

  onSelect(scenario : Scenario) : void {
      this.selectedScenario = scenario;
      //this.dataService.getDatasets(scenario.uid)
    }

  delete(scenario : Scenario) : void {
    this.dataService.deleteScenario(scenario.uid)
       .then(() => {
         this.scenarios = this.scenarios.filter(s => s !== scenario);
         if (this.selectedScenario === scenario) { this.selectedScenario = null; }
       });
  }
  add(name : string): void {
    var scenario = new Scenario(name,name)
    let that = this;
    this.dataService.addScenario(scenario).then( function(){ that.scenarios.push(scenario); });
  }
}
