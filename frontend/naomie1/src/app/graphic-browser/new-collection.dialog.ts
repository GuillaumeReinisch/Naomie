import { Component , Inject}    from '@angular/core';

import {MdDialog, MdDialogRef,MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material';


@Component({
  selector: 'new-collection-dlg',
  templateUrl: './new-collection.dialog.html',
  styleUrls: ['./new-collection.dialog.css']
})
export class NewCollectionDialog {
  constructor(public dialogRef: MdDialogRef<NewCollectionDialog>,
  @Inject(MD_DIALOG_DATA) public data: any) {}

  public useNamespace = true;
  
}
