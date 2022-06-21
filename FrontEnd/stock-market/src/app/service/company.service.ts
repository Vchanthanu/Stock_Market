import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  public domainUrl = environment.baseUrl + "company/";

  getAllCompanyUrl = this.domainUrl + "all";
  registerCompanyUrl = this.domainUrl + "register";
  constructor(private http: HttpClient) { }

  getAllCompany() {
    return this.http.get(this.getAllCompanyUrl);
  }

  registerCompany(req: any) {
    return this.http.post(this.registerCompanyUrl, req);
  }
}
