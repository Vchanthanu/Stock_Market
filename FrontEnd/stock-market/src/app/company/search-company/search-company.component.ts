import { Component, OnInit } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { CellLinkComponent } from 'src/app/common/cell-link/cell-link.component';

@Component({
  selector: 'app-search-company',
  templateUrl: './search-company.component.html',
  styleUrls: ['./search-company.component.css']
})
export class SearchCompanyComponent implements OnInit {
  public columnDefs: any;
  public defaultColDef: any;
  public rowData:any = [];
  constructor() { }

  ngOnInit(): void {
    this.columnDefs = [
      { headerName:'Company Code' ,field: 'companyCode'},
      { headerName:'Company Name' ,field: 'companyName'},
      { headerName:'Stock Exchange' ,field: 'stockExchange'},
      { headerName:"Last Trade Price" ,field: 'lastTradePrice'},
      { headerName:"Action",cellRenderer: CellLinkComponent }
    ];
    this.defaultColDef = {
      sortable: true
    };

    this.rowData=[
    {'companyCode':"SAIL",'companyName':"SAIL",'stockExchange':"BSE",'lastTradePrice':12},
    {'companyCode':"SAIL",'companyName':"SAIL",'stockExchange':"BSE",'lastTradePrice':12}
    ]
  }
  onGridReady(event:any){

  }
  onMouseOver(event:any){

  }
}
