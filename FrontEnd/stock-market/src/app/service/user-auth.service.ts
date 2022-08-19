import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {
  loggedIn: boolean = false;
  user: string | undefined;
  constructor() { }

  public setUser(user: string) {
    this.user = user;
  }
  public getUser() {
    return this.user;
  }
  public setLog(loggedIn: boolean) {
    this.loggedIn = loggedIn;
  }
  public getLog() {
    return this.loggedIn;
  }
}
