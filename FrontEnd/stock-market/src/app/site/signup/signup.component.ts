import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Role } from 'src/app/model/role';
import { User } from 'src/app/model/user';
import { AuthenticationService } from 'src/app/service/authentication.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  signupForm : FormGroup  ;
  user!: User;
  role: Role | undefined;
  error:string='';
  loader: boolean= false;
  constructor(private authenticationService: AuthenticationService,private router:Router) {
    this.signupForm = new FormGroup({
      userName: new FormControl(null, [Validators.required]),
      email: new FormControl(null, [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$')]),
      mobileNumber: new FormControl(null, [Validators.required, Validators.pattern('^[0-9+]*'), Validators.maxLength(13)]),
      password: new FormControl(null, [Validators.required]),
      confirmPassword: new FormControl(null, [Validators.required]),
    });
   }

  ngOnInit() {

  }

  get userName() { return this.signupForm.get('userName'); }
  get email() { return this.signupForm.get('email'); }
  get mobileNumber() { return this.signupForm.get('mobileNumber'); }
  get confirmPassword() { return this.signupForm.get('confirmPassword'); }
  get password() { return this.signupForm.get('password'); }


  onSubmit() {
    this.user = this.signupForm.value;
    this.loader=true;
    this.authenticationService.addUser(this.user).subscribe((data:any) => {
      this.loader=false;
      this.router.navigate(['login']);
    },
    (responseError:any) => {
        this.error = responseError.error.message;
        this.loader=false;
      }
    );
  }
}
