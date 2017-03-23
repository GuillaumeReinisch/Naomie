import { Component, OnInit } from '@angular/core';
import { NewCollectionDialog } from '../graphic-browser/new-collection.dialog'
import { NewParameterDialog } from './new-parameter.dialog'
import { MdDialog, MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material'
import { ParametersService }  from '../parameters.service'
import{ Collection , CollectionForm} from "../models/collection"
import{ Parameter , ParameterForm} from "../models/parameter"
import { LocalStorageService }  from 'angular-2-local-storage';

@Component({
  selector: 'app-parameters-browser',
  providers:[ ParametersService],
  templateUrl: './parameters-browser.component.html',
  styleUrls: ['./parameters-browser.component.css']
})
export class ParametersBrowserComponent implements OnInit {


    public collections: Collection[]
    public variables: string[]
    public loading : boolean = false
    public variablesLoading : boolean = false;
    public userName: string;
    public currentClass = "col-md-8";

    public editionMode = false;

    constructor(public dialog : MdDialog,
      private parametersService : ParametersService,
      private localStorageService: LocalStorageService
    ) { }

    ngOnInit() {
      this.userName = <string> this.localStorageService.get('user')
      this.loading = true;
      this.variablesLoading = true;
      this.parametersService.getCollections(this.userName).then( collections =>
        { this.collections = collections;
          this.loading = false;
          for(let i=0; i<this.collections.length; i++){
            this.parametersService.getParametersSummary(collections[i].name).then( parameters =>
              this.collections[i].parametersSummary = parameters)
          }
        } );

      this.parametersService.getParameters().then( params =>
          { console.log(params);
          } );
      this.parametersService.getVariables().then( variables =>
        { this.variables = variables;
          this.variablesLoading= false;}
         );
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
          variables: [],
          loading : true,
          collections : []
        }
      };

    onNewCollection() : void {
      let dialog = this.dialog.open(NewCollectionDialog,this.config);
      dialog.afterClosed().subscribe(form => {
          this.parametersService.createCollection(new CollectionForm(form.name, this.userName, form.description,this.getDate(), form.namespace));
      });
    }

    onNewParameter() : void {
      this.config.data.variables   = this.variables;
      this.config.data.loading     = this.variablesLoading;
      this.config.data.collections = this.collections;
      let dialog = this.dialog.open(NewParameterDialog,this.config);
      dialog.afterClosed().subscribe(form => {

          let data = new Map<string,string>();
          let vars =  this.config.data.InputVars; // vars.foreach( obj => data[obj.name] = obj.value )
          for(let i =0 ; i<  vars.length; i++ )
             data[vars[i].name] = vars[i].value

          let parameter = new ParameterForm(form.formula,data,this.userName,this.getDate(),form.selectedCollection )
          this.parametersService.createParameter(parameter);
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
