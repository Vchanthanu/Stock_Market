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
  role: string | undefined;
  user: string | undefined;
  token: string | undefined;
  private authenticationApiUrl = environment.baseUrl + '/authentication-service';
  constructor(private httpClient: HttpClient, private userAuthService: UserAuthService) { }

  authenticate(user: string, password: string): Observable<any> {
    let credentials = btoa(user + ':' + password);
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Basic ' + credentials);
    return this.httpClient.get(this.authenticationApiUrl + '/authenticate', { headers });
  }

  addUser(user: User): Observable<void> {
    return this.httpClient.post<void>(this.authenticationApiUrl + '/users', user);
  }

  modifyUser(user: User) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.getToken()
      })
    };
    return this.httpClient.put<void>(this.authenticationApiUrl + "/users/" + this.userAuthService.getUser(), user, httpOptions);
  }
  public getUsername(username: string): Observable<User> {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + this.getToken()
      })
    };
    return this.httpClient.get<User>(this.authenticationApiUrl + '/users/' + username, httpOptions);
  }

  public setToken(token: string|undefined) {
    this.token = token;
  }

  public getToken() {
    return this.token;
  }
}
