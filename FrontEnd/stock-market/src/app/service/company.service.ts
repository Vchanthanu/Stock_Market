import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  public domainUrl = environment.baseUrl + "company/";

  private getAllCompanyUrl = this.domainUrl + "all";
  private registerCompanyUrl = this.domainUrl + "register";
  private deleteCompanyUrl = this.domainUrl + "delete/";
  private getCompanyByCodeUrl = this.domainUrl + "get/";
  private searchByCompanyNameUrl = this.domainUrl + "search/"
  constructor(private http: HttpClient) { }

  getAllCompany() {
    return this.http.get(this.getAllCompanyUrl);
  }

  searchByCompanyName(key: string) {
    return this.http.get(this.searchByCompanyNameUrl + key)
  }

  deleteCompany(companyCode: String) {
    return this.http.delete(this.deleteCompanyUrl + companyCode);
  }

  getCompanyByCode(companyCode: String) {
    return this.http.get(this.getCompanyByCodeUrl + companyCode);
  }

  registerCompany(req: any) {
    return this.http.post(this.registerCompanyUrl, req);
  }
}
