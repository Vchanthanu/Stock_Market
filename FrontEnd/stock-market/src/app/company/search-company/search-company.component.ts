import { Component, OnInit } from '@angular/core';
import { ColDef } from 'ag-grid-community';

@Component({
  selector: 'app-search-company',
  templateUrl: './search-company.component.html',
  styleUrls: ['./search-company.component.css']
})
export class SearchCompanyComponent implements OnInit {
  public columnDefs: any;
  public defaultColDef: any;
  public rowData:[] = [];
  constructor() { }

  ngOnInit(): void {
    this.columnDefs = [
      { name:'Company Code' ,field: 'companyCode' },
      { name:'Company Name' ,field: 'companyName ' },
      { name:'Stock Exchange' ,field: 'stockExchange ' },
      { name:'Last Trade Price' ,field: 'price' }
    ];
    this.defaultColDef = {
      sortable: true,
      // filter: true,
    };
  }
  onGridReady(event:any){

  }
}
