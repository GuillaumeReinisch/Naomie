import { Component,Inject, OnInit, Output, EventEmitter }    from '@angular/core';
import { ProjectsService}       from '../projects.service';
import { NumericalDataService}  from '../numerical-data.service';
import { Project, ProjectForm } from '../models/project';
import { User }                 from '../models/user';
import { LocalStorageService }  from 'angular-2-local-storage';
import { Router }               from '@angular/router';
import {MdDialog, MdDialogRef,MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material';
import {Dataset} from '../models/numerical-data'


@Component({
  selector: 'new-project',
  templateUrl: './dialog-new-project.html',
})
export class DialogNewProject {
  constructor(public dialogRef: MdDialogRef<DialogNewProject>,
  @Inject(MD_DIALOG_DATA) public data: any) {}
}


@Component({
  selector: 'dialog-scenarios',
  templateUrl: './dialog-scenarios.html',
  styleUrls: ['./dashboard.component.css']
})
export class DialogScenarios {
  constructor(public dialogRef: MdDialogRef<DialogScenarios>,
  @Inject(MD_DIALOG_DATA) public data: any) {}

  selectedDataset: Dataset;

  getHHeaders(dataset : Dataset) : string[] {
    return Object.keys(dataset.columns)
  }
  getValues(dataset : Dataset, lineIndex: number ) : number[] {

    var values = new Array<number>()
    Object.keys(dataset.columns).forEach( header => values.push(dataset.columns[header][lineIndex]))
    return values
  }

  onSelect(dataset : Dataset) : void {
    this.selectedDataset = dataset;
  }
}




@Component({
  selector: 'app-dashboard',
  providers:[ProjectsService,NumericalDataService],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(public dialog : MdDialog,
              private projectsService: ProjectsService,
              private numericalDataService : NumericalDataService,
              private localStorageService: LocalStorageService,
              private _router: Router) { }

  loading = true;
  userName : string;
  projects : Project[];
  selectedProject : Project;

  ngOnInit(): void {

    this.userName = <string> this.localStorageService.get('user');
    if(this.userName == null )
      this._router.navigate(['/login']);

    this.projectsService.getProjects(this.userName).then( projects =>
      { this.projects = projects;
        this.loading = false;
      } );
  }

  selectedOption: string;

  config: MdDialogConfig = {
      disableClose: false,
      width: '',
      height: '',
      position: {
        top: '',
        bottom: '',
        left: '',
        right: ''
      },
      data: {
        scenario: '',
        datasets: [],
        loading : true,
      }
    };

  openDialog() {
    let dialogRef = this.dialog.open(DialogNewProject,this.config);

    dialogRef.afterClosed().subscribe(form => {
      var projectFrom = new ProjectForm(form.name,this.userName,this.getDate(), form.description)
      console.log(projectFrom)
      this.projectsService.createProject(projectFrom);
    });
  }

  @Output() notify: EventEmitter<User> = new EventEmitter<User>();

  onSelect(project : Project) : void {
    this.selectedProject = project;
  }

  onSelectScenario(scenario: string) : void {

    this.config.data.scenario = scenario;
    this.config.data.loading = true;
    this.numericalDataService.getDatasets(scenario).then( datasets =>
      {this.config.data.datasets = datasets;
        console.log(datasets);
       this.config.data.loading = false;});

    let dialogScenario = this.dialog.open(DialogScenarios,this.config);

    dialogScenario.afterClosed().subscribe(form => {
    });

  }


  getDate() :string{

    var today = new Date();
    var dd : any   = today.getDate();
    var mm : any   = today.getMonth()+1;
    var yyyy  = today.getFullYear();

    if(dd<10)
      dd='0'+dd.toString()
    if(mm<10)
      mm='0'+mm.toString()
    return  mm+'/'+dd+'/'+yyyy;
  }
}
