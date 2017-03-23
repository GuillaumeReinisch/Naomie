import { Component, OnInit } from '@angular/core';
import { NewCollectionDialog } from './new-collection.dialog'
import { MdDialog, MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material';
import { GraphicsService }  from '../graphic.service'
import { ParametersService }  from '../parameters.service'
import{ Collection , CollectionForm} from "../models/collection"
import { LocalStorageService }  from 'angular-2-local-storage';

@Component({
  selector: 'app-graphic-browser',
  providers:[GraphicsService, ParametersService],
  templateUrl: './graphic-browser.component.html',
  styleUrls: ['./graphic-browser.component.css']
})
export class GraphicBrowserComponent implements OnInit {

  public collections: Collection[]
  public loading : boolean = false
  public userName: string;
  public currentClass = "col-md-8";

  public editionMode = false;

  constructor(public dialog : MdDialog,
    private graphicsService : GraphicsService,
    private parametersService : ParametersService,
    private localStorageService: LocalStorageService
  ) { }

  ngOnInit() {
    this.userName = <string> this.localStorageService.get('user')
    this.parametersService.getCollections(this.userName).then( collections =>
      { this.collections = collections;
        this.loading = false;
      } );
  }


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

  onNewCollection() : void {
    let dialog = this.dialog.open(NewCollectionDialog,this.config);
    dialog.afterClosed().subscribe(form => {
        this.parametersService.createCollection(new CollectionForm(form.name, this.userName, form.description,this.getDate(), form.namespace));
    });
  }

  onGraphicEdition() : void {
    console.log("onGraphicEdition")
    this.currentClass = "col-md-4"
    this.editionMode  = true
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
