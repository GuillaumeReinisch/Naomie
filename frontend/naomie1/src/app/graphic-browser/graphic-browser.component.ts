import { Component, OnInit } from '@angular/core';
import { NewCollectionDialog } from './new-collection.dialog'
import { MdDialog, MdDialogConfig, MD_DIALOG_DATA}  from '@angular/material';
import { GraphicsService }  from '../graphic.service'
import{ GraphicCollection , GraphicCollectionForm} from "../models/graphic"

@Component({
  selector: 'app-graphic-browser',
  providers:[GraphicsService],
  templateUrl: './graphic-browser.component.html',
  styleUrls: ['./graphic-browser.component.css']
})
export class GraphicBrowserComponent implements OnInit {

  constructor(public dialog : MdDialog, private graphicsService :GraphicsService) { }

  ngOnInit() {
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
        this.graphicsService.createCollection(new GraphicCollectionForm(form.name, "", form.description));
    });

  }
}
