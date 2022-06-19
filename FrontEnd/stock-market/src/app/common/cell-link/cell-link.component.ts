import { Component, OnInit } from '@angular/core';
import { AgFrameworkComponent, ICellRendererAngularComp } from 'ag-grid-angular';
import { ICellRendererParams } from 'ag-grid-community';

@Component({
  selector: 'app-cell-link',
  templateUrl: './cell-link.component.html',
  styleUrls: ['./cell-link.component.css']
})
export class CellLinkComponent implements ICellRendererAngularComp  {

  constructor() { }
  agInit(params: ICellRendererParams): void {
  }

  // gets called whenever the user gets the cell to refresh
  refresh(params: ICellRendererParams) {
    return true;
  }

}
