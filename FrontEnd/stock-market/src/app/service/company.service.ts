import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AuthenticationService } from './authentication.service';

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
  constructor(private http: HttpClient,private auth : AuthenticationService) { }

  getAllCompany() {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.get(this.getAllCompanyUrl,httpOptions);
  }

  searchByCompanyName(key: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.get(this.searchByCompanyNameUrl + key, httpOptions)
  }

  deleteCompany(companyCode: String) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.delete(this.deleteCompanyUrl + companyCode,httpOptions);
  }

  getCompanyByCode(companyCode: String) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.get(this.getCompanyByCodeUrl + companyCode,httpOptions);
  }

  registerCompany(req: any) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.auth.getToken()
      })
    };
    return this.http.post(this.registerCompanyUrl, req,httpOptions);
  }
}
