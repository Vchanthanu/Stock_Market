import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { dateValidator } from 'src/app/model/date-validator';
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
  stockExchangeList: any = [];
  errorMsg = '';
  stockPriceList: any = [];
  isAdded: boolean = false;
  public stockPriceData: any;
  public noRowsTemplate: any;
  loader: boolean = false;
  show: boolean = false;
  constructor(private fb: FormBuilder, private router: Router, private companyService: CompanyService, private stockService: StockPriceService, private pipe: DatePipe) { }

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
      stockPrice: new FormControl(null, [Validators.required, Validators.pattern('^-?[0-9]\\d*(\\.\\d*)?$')])
    });

    this.viewStockPriceForm = this.fb.group({
      startDate: ['', [
        Validators.required
      ]],
      endDate: ['', [
        Validators.required
      ]],
      stockExchangeCde: ['', [
        Validators.required
      ]],
    }, { validator: dateValidator('startDate', 'endDate') });
  }

  get stockExchangeCode() { return this.stockPriceForm.get('stockExchangeCode'); }
  get stockPrice() { return this.stockPriceForm.get('stockPrice'); }
  get startDate() { return this.viewStockPriceForm.get('startDate'); }
  get endDate() { return this.viewStockPriceForm.get('endDate'); }
  get stockExchangeCde() { return this.viewStockPriceForm.get('stockExchangeCde'); }

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
      this.loader = true;
      this.stockService.addStockPrice(req).subscribe((data: any) => {
        setTimeout(() => {
          this.getCompanyDetail();
        }, 300);
        this.loader = false;
        this.isAdded = true;
        this.stockPriceForm.reset();
        this.isAdded = false;
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
    this.loader = true;
    this.companyService.deleteCompany(this.company.code).subscribe((data: any) => {
      setTimeout(() => {
        this.router.navigate(['company']);
      }, 300);


    }, (error: any) => {
      this.errorMsg = error.message;
    })
  }
  getStockPriceBasedOnDate() {
    this.stockService.getStockPriceBasedOnDate(this.getDateString(this.viewStockPriceForm.get('startDate').value), this.getDateString(this.viewStockPriceForm.get('endDate').value), this.viewStockPriceForm.get('stockExchangeCde').value, this.company.code).subscribe((data: any) => {
      this.stockPriceData = data;
      if (data != null && data.priceList != null)
        this.rowData = data.priceList;
      else {
        this.rowData = [];
        this.show = true;
      }
    }, (error: any) => {
      if (error.status == 204) {
        this.show = true;
        this.rowData = [];
      }
      this.errorMsg = error.message;
    })
  }
  getDateString(date: any) {
    let formattedDate = this.pipe.transform(date, 'yyyy-MM-dd');
    if (formattedDate != undefined) {
      return formattedDate.toString();
    }
    return null;
  }

}
