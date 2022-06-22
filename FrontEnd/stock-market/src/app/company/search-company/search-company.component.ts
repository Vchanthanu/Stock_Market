import { Component, OnInit } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { CellLinkComponent } from 'src/app/common/cell-link/cell-link.component';
import { CompanyService } from 'src/app/service/company.service';

@Component({
  selector: 'app-search-company',
  templateUrl: './search-company.component.html',
  styleUrls: ['./search-company.component.css']
})
export class SearchCompanyComponent implements OnInit {
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any = [];
  constructor(private companyService: CompanyService) { }

  ngOnInit(): void {
    this.gridConfig();
    this.companyService.getAllCompany().subscribe((data: any) => {
      this.rowData = data
    })
  }

  gridConfig() {
    this.columnDefs = [
      { headerName: 'Company Code', field: 'code' },
      { headerName: 'Company Name', field: 'name' },
      { headerName: 'CEO', field: 'ceo' },
      { headerName: 'Stock Exchange', valueGetter: (params: any) => { return params.data.stockPrice[0].stockExchange.name } },
      { headerName: "Last Trade Price", valueGetter: (params: any) => { return params.data.stockPrice[0].stockPrice } },
      { headerName: "Action", cellRenderer: CellLinkComponent }
    ];
    this.defaultColDef = {
      sortable: true
    };
  }
  onGridReady(params: any) {
    params.api.sizeColumnsToFit();
  }
  onMouseOver(event: any) {

  }
}
