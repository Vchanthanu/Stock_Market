import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserAuthService {
  loggedIn: boolean = false;
  role: string | undefined;
  user: string | undefined;
  constructor() { }

  public setRole(role: string) {
    this.role = role;
  }
  public getRole() {
    return this.role;
  }
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
