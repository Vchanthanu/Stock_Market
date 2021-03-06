import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { CompanyService } from 'src/app/service/company.service';
import { StockPriceService } from 'src/app/service/stock-price.service';

@Component({
  selector: 'app-add-company',
  templateUrl: './add-company.component.html',
  styleUrls: ['./add-company.component.css']
})
export class AddCompanyComponent implements OnInit {
  addCompany: FormGroup|any ;
  stockPriceForm: FormGroup | any;
  stockExchangeList: any;
  stockPriceList: any = [];
  errorMsg = '';
  constructor(private stock: StockPriceService, private companyService: CompanyService, private router: Router) { }

  ngOnInit(): void {
    this.declareForm();
    this.getAllStockExchange();
  }

  getAllStockExchange() {
    this.stock.getAllStockExchage().subscribe((data: any) => {
      this.stockExchangeList = data;
    });
  }

  declareForm() {
    this.addCompany = new FormGroup({
      code: new FormControl(null, Validators.required),
      name: new FormControl(null, Validators.required),
      ceo: new FormControl(null, Validators.required),
      turnover: new FormControl(null, Validators.required),
      website: new FormControl(null, Validators.required),
    });

    this.stockPriceForm = new FormGroup({
      stockExchangeCode: new FormControl(null, Validators.required),
      stockPrice: new FormControl(null, Validators.required)
    });
  }

  get code() { return this.addCompany.get('code'); }
  get name() { return this.addCompany.get('name'); }
  get ceo() { return this.addCompany.get('ceo'); }
  get turnover() { return this.addCompany.get('turnover'); }
  get website() { return this.addCompany.get('website'); }
  get stockExchangeCode() { return this.stockPriceForm.get('stockExchangeCode'); }
  get stockPrice() { return this.stockPriceForm.get('stockPrice'); }

  onAddStockPrice() {
    this.errorMsg = "";
    if (this.stockPriceForm.valid) {
      this.stockPriceList.push(this.stockPriceForm.value);
    } else {
      this.errorMsg = "Both fields are required";
    }
  }

  onSubmit() {
    this.errorMsg = "";
    if (this.addCompany.valid && this.stockPriceList.length > 0) {
      let req = this.addCompany.value
      req.turnover = Number(req.turnover)
      let reqStockPriceList: any = [];
      this.stockPriceList.map((data: any) => {
        reqStockPriceList.push(
          {
            "id": {
              "companyCode": req.code,
              "stockExchangeCode": data.stockExchangeCode
            },
            "stockPrice":Number( data.stockPrice)
          });
      });
      req.stockPrice = reqStockPriceList;
      this.companyService.registerCompany(req).subscribe((data: any) => {
        this.router.navigate(['company']);
      }, (error: any) => {
        this.errorMsg = error.message;
      })
    } else {
      this.errorMsg = "All fields are required"
    }
  }
}
