import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/service/company.service';
import { StockPriceService } from 'src/app/service/stock-price.service';

@Component({
  selector: 'app-detail-company',
  templateUrl: './detail-company.component.html',
  styleUrls: ['./detail-company.component.css']
})
export class DetailCompanyComponent implements OnInit {
  company: any;
  viewStockPriceForm: FormGroup | any;
  public columnDefs: any;
  public defaultColDef: any;
  public rowData: any = [];
  stockPriceForm: any;
  stockExchangeList: any;
  errorMsg = '';
  stockPriceList: any = [];
  isAdded: boolean = false;
  public stockPriceData: any;
  public noRowsTemplate: any;
  constructor(private router: Router, private companyService: CompanyService, private stockService: StockPriceService, private pipe: DatePipe) { }

  ngOnInit(): void {
    this.declareForm();
    this.gridConfig();
    this.getStockExchange();
    this.getCompanyDetail();
  }

  getCompanyDetail() {
    let sessionCod: any = sessionStorage.getItem("companyCode");
    this.companyService.getCompanyByCode(JSON.parse(sessionCod)).subscribe((data: any) => {
      this.company = data;
      let companyExchangeList = data.stockPrice.map((sp: any) => sp.stockExchange.code);
      this.stockExchangeList.filter((ex: any) => companyExchangeList.includes(ex.code));

    });
  }
  getStockExchange() {
    this.stockService.getAllStockExchage().subscribe((data: any) => {
      this.stockExchangeList = data;
    });
  }

  declareForm() {
    this.stockPriceForm = new FormGroup({
      stockExchangeCode: new FormControl(null, Validators.required),
      stockPrice: new FormControl(null, Validators.required)
    });
    this.viewStockPriceForm = new FormGroup({
      startDate: new FormControl(null, Validators.required),
      endDate: new FormControl(null, Validators.required),
      stockExchangeCode: new FormControl(null, Validators.required)

    });
  }

  get stockExchangeCode() { return this.stockPriceForm.get('stockExchangeCode'); }
  get stockPrice() { return this.stockPriceForm.get('stockPrice'); }

  getCompanyStockPrice(req?: any) {
    if (req == null) {
      req = { code: this.company.code }
    }
    this.stockService.getStockPrice(req).subscribe((data: any) => {
      this.stockPriceList = data
    });
  }

  onAddStockPrice() {
    this.errorMsg = "";
    if (this.stockPriceForm.valid) {
      let req =
        [{
          "id": {
            "companyCode": this.company.code,
            "stockExchangeCode": this.stockPriceForm.get('stockExchangeCode').value,
          },
          "stockPrice": Number(this.stockPriceForm.get('stockPrice').value)
        }]
      this.stockService.addStockPrice(req).subscribe((data: any) => {
        this.isAdded = true;
        this.stockPriceForm.reset();
        this.getCompanyDetail();
      })

    } else {
      this.errorMsg = "Both fields are required";
    }
  }

  gridConfig() {
    this.columnDefs = [
      { headerName: 'Stock Exchange', field: "stockExchange.name" },
      { headerName: 'Price', field: 'stockPrice' },
      { headerName: 'Updated Date', valueGetter: (params: any) => { return this.pipe.transform(params.data.priceUpdatedDate, 'dd/MM/yyyy HH:mm:ss') } },
    ];
    this.defaultColDef = {
      sortable: true
    };
  }
  onGridReady(params: any) {
    params.api.sizeColumnsToFit();
  }

  deleteCompany() {
    this.companyService.deleteCompany(this.company.code).subscribe((data: any) => {
      this.router.navigate(['company']);
    }, (error: any) => {
      this.errorMsg = error.message;
    })
  }
  getStockPriceBasedOnDate() {
    console.log("inside get stockprice");
    this.stockService.getStockPriceBasedOnDate(this.getDateString(this.viewStockPriceForm.get('startDate').value), this.getDateString(this.viewStockPriceForm.get('endDate').value), this.viewStockPriceForm.get('stockExchangeCode').value, this.company.code).subscribe((data: any) => {
      this.stockPriceData = data;
      this.rowData = data.priceList;

    }, (error: any) => {
      if (error.status == 204) {
        this.noRowsTemplate =
        `"<span">no rows to show</span>"`;

      }
      this.errorMsg = error.message;
    })


  }
  getDateString(date: any) {
    console.log("date" + date);
    let formattedDate = this.pipe.transform(date, 'yyyy-MM-dd');
    if (formattedDate != undefined) {
      return formattedDate.toString();

    }
    return null;

  }


}
