import { Component, OnInit } from '@angular/core';
import { ColDef } from 'ag-grid-community';
import { CellLinkComponent } from 'src/app/common/cell-link/cell-link.component';
import { CompanyService } from 'src/app/service/company.service';
import { AddCompanyComponent } from '../add-company/add-company.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-search-company',
  templateUrl: './search-company.component.html',
  styleUrls: ['./search-company.component.css']
})
export class SearchCompanyComponent implements OnInit {
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any = [];
  public error: string = '';
  constructor(private companyService: CompanyService
    , private modalService: NgbModal
  ) { }

  ngOnInit(): void {
    this.gridConfig();
    this.getAllCompanies();
  }

  private getAllCompanies() {
    this.companyService.getAllCompany().subscribe((data: any) => {
      this.rowData = data;
    },
      (responseError) => {
        if (responseError.status == 204) {
          this.error = responseError.error.message;
        }
        else {
          this.error = "System is currently unavailable Please try again later";
        }
      });
  }

  gridConfig() {
    this.columnDefs = [
      { headerName: 'Company Code', field: 'code' },
      { headerName: 'Company Name', field: 'name' },
      { headerName: 'CEO', field: 'ceo' },
      { headerName: 'Stock Exchange', valueGetter: (params: any) => { return params.data.stockPrice[0].stockExchange.code } },
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
  onMouseOver(params: any) {
    sessionStorage.setItem("companyCode", JSON.stringify(params.data.code));
  }

  onKeypressEvent(event: any) {
    if (event.target.value == null || event.target.value == "") {
      this.getAllCompanies();
    }
    else {
      this.companyService.searchByCompanyName(event.target.value).subscribe((data: any) => {
        this.rowData = data;
      },
        (responseError) => {

          if (responseError.status == 204) {
            this.error = responseError.error.message;
            this.rowData = [];
          }
          else {
            this.rowData = [];
            this.error = "System is currently unavailable Please try again later";
          }
        });
    }
  }
  addCompany() {
    const modalRef = this.modalService.open(AddCompanyComponent, { windowClass: "modal-lg-class", centered: true });
    modalRef.result.then(res => {
      setTimeout(() => {
        this.getAllCompanies()
      }, 5000);

    })
  }
}
