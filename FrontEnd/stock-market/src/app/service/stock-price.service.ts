import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StockPriceService {
  public domainUrl = environment.baseUrl+"stock/";

  getAllStockExchageUrl = this.domainUrl+"get/stockExchange";
  constructor(private http:HttpClient) { }

  getAllStockExchage(){
    return this.http.get(this.getAllStockExchageUrl);
  }
}
