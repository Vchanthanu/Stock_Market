import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../model/user';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  loggedIn: boolean | undefined;
  user: string | undefined;
  token: string | undefined;
  private authenticationApiUrl = environment.baseUrl + 'authentication/user';
  constructor(private httpClient: HttpClient, private userAuthService: UserAuthService) { }

  authenticate(user: string, password: string): Observable<any> {
    let credentials = btoa(user + ':' + password);
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'authHeader ' + credentials);
    return this.httpClient.get(this.authenticationApiUrl + '/login', { headers });
  }

  addUser(user: User): Observable<void> {
    return this.httpClient.post<void>(this.authenticationApiUrl + '/signup', user);
  }

 
  public setToken(token: string|undefined) {
    this.token = token;
  }

  public getToken() {
    return this.token;
  }
}
