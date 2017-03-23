import { Component , Inject}    from '@angular/core';

import {MdDialog, MdDialogRef,MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import 'rxjs/add/operator/startWith';

export class InputVar{
  constructor(public name:string, public value:string){}
}

@Component({
  selector: 'new-parameter-dlg',
  templateUrl: './new-parameter.dialog.html',
  styleUrls: ['./new-parameter.dialog.css']
})
export class NewParameterDialog {

  public variableCompletionCtrl : FormControl;
  public filteredVariables : any;

  public inputVariables = [ new InputVar("x","example1")  ]
  private inputVarNames = ["x","y","z","u","v","w"]

  public selectedCollection : string;

  constructor(public dialogRef: MdDialogRef<NewParameterDialog>,
  @Inject(MD_DIALOG_DATA) public data: any) {
    this.variableCompletionCtrl = new FormControl();

    this.filteredVariables = this.variableCompletionCtrl.valueChanges
        .startWith(null)
        .map(name => this.filterVariable(name));

    this.data.InputVars = this.inputVariables;
  }


  addVariable(){
    let name = this.inputVarNames[this.inputVariables.length]
    this.inputVariables.push(new InputVar(name,"") )
  }
  removeVariable(inputVar : InputVar ){
    this.inputVariables = this.inputVariables.filter( item => {return item.name != inputVar.name} )
    let that = this
    this.inputVariables = this.inputVariables.map( function(item, index) {
        return new InputVar(that.inputVarNames[index],item.value);} )

  }

  filterVariable(val: any) {
    return val ? this.data.variables.filter((s) => new RegExp(val, 'gi').test(s)) :  this.data.variables;
  }

  onInputVarChange(val : any, inputVar : any ) {
    inputVar.value = val
  }

}
