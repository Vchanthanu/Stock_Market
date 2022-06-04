import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/service/authentication.service';
import { UserAuthService } from 'src/app/service/user-auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: any;
  formValidation: boolean = true;
  error: String = '';
  loader: boolean = false;
  constructor(private formbuilder: FormBuilder, private router: Router, private authenticationService: AuthenticationService, private userAuthService: UserAuthService) {

  }

  ngOnInit() {
    this.loginForm = this.formbuilder.group({
      emailId: ['', [
        Validators.required
      ]],
      password: ['', [
        Validators.required
      ]]
    })
  }

  get emailId() { return this.loginForm.get('emailId'); }
  get password() { return this.loginForm.get('password'); }

  login() {
    if (this.loginForm.valid) {
      this.authenticationService.authenticate(this.emailId.value, this.password.value).subscribe((data: any) => {
        this.formValidation = true;
        this.userAuthService.setUser(this.emailId.value);
        this.authenticationService.setToken(data.token);
        this.userAuthService.setRole(data.role);
        this.userAuthService.setLog(true);
        if (data.role == "ADMIN") {
          this.router.navigate(["admin"]);
        } else {
          this.router.navigate(["user"]);
        }

      },
        (error: any) => {
          this.formValidation = false;
          if (error.status == 401) {
            this.error = "Invalid email Id/Password";
          }
        });
    } else {

    }
  }

}
