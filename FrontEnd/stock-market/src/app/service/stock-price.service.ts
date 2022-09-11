import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';

@Injectable({
  providedIn: 'root'
})
export class StockPriceService {
  public domainUrl = environment.baseUrl + "stock/";

  getAllStockExchageUrl = this.domainUrl + "get/stockExchange";
  addStockPriceUrl = this.domainUrl + "add";
  getStockPriceByCompanyCodeUrl = this.domainUrl + "get/"
  constructor(private http: HttpClient, private auth: AuthenticationService) { }

  getAllStockExchage() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.get(this.getAllStockExchageUrl, httpOptions);
  }
  addStockPrice(req: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.post(this.addStockPriceUrl, req, httpOptions)
  }

  getStockPrice(req: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.get(this.getStockPriceByCompanyCodeUrl + req.code, httpOptions)
  }
  getStockPriceBasedOnDate(startDate: any, endDate: any, stockExchangeCode: string, companyCode: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      }),
      params: new HttpParams().set("stockExchangeCode", stockExchangeCode).set("fromDate", startDate).
        set("toDate", endDate)
    };
    

    return this.http.get(this.getStockPriceByCompanyCodeUrl + companyCode, httpOptions)
  }
}
