import { Component }  from '@angular/core';
import { Graphic}      from '../models/data-mockup'
import { OnInit }     from '@angular/core';
import { GraphicService } from '../services/graphic.service';

@Component({
  selector: 'graphic-browser',
  providers:[GraphicService],
  template: `<h2>graphic list</h2>
  <ul class="graphics">
    <li *ngFor="let graph of graphics"
      [class.selected]="graph === selectedGraphic"
      (click)="onSelect(graph)">
      <span class="badge">{{graph.name}}</span> {{graph.uid}}
    </li>
  </ul>
  `,
  styles: [`
    .selected {
      background-color: #CFD8DC !important;
      color: white;
    }
    .graphics {
      margin: 0 0 2em 0;
      list-style-type: none;
      padding: 0;
      width: 25em;
    }
    .graphics li {
      cursor: pointer;
      position: relative;
      left: 0;
      background-color: #EEE;
      margin: .5em;
      padding: .3em 0;
      height: 1.6em;
      border-radius: 4px;
    }
    .graphics li.selected:hover {
      background-color: #BBD8DC !important;
      color: white;
    }
    .graphics li:hover {
      color: #607D8B;
      background-color: #DDD;
      left: .1em;
    }
    .graphics .text {
      position: relative;
      top: -3px;
    }
    .graphics .badge {
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


export class GraphicBrowserComponent implements OnInit {

  graphics: Graphic[];

  selectedGraphic : Graphic;

  constructor(private graphsService: GraphicService) { }

  ngOnInit(): void {
    this.graphsService.getGraphics().then(graphs => this.graphics = graphs);
   }

  onSelect(graph : Graphic) : void {
     this.selectedGraphic = graph;
   }
}
