import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm: any;
  user!: User;
  error: string = '';
  constructor(private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
    this.signupForm = new FormGroup({
      userName: new FormControl(null, [Validators.required, Validators.minLength(3)]),
      email: new FormControl(null, [Validators.required, Validators.email
      ]),
      mobileNumber: new FormControl(null, [Validators.required, Validators.pattern('^[0-9+]*')]),
      password: new FormControl(null, [Validators.required, Validators.minLength(6)]),
    });
  }

  get userName() { return this.signupForm.get('userName'); }
  get email() { return this.signupForm.get('email'); }
  get mobileNumber() { return this.signupForm.get('mobileNumber'); }
  get confirmPassword() { return this.signupForm.get('confirmPassword'); }
  get password() { return this.signupForm.get('password'); }


  onSubmit() {
    this.user = this.signupForm.value;
    this.authenticationService.addUser(this.user).subscribe((data: any) => {
      if (data.status) {
        this.router.navigate(['login']);
      } else {
        this.error = data.message;
      }
    },
      (responseError: any) => {
        this.error = responseError.error.message;
      }
    );
  }
}
