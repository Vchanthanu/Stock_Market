import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  public domainUrl = environment.baseUrl+"company/";

  getAllCompaniesUrl = this.domainUrl+"all";
  constructor(private http:HttpClient) { }

  getAllCompanies(){
    return this.http.get(this.getAllCompaniesUrl);
  }
}
