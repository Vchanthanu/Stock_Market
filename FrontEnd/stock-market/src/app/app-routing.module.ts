import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SearchCompanyComponent } from './company/search-company/search-company.component';
import { LoginComponent } from './site/login/login.component';
import { SignupComponent } from './site/signup/signup.component';

const routes: Routes = [
  {path:'',redirectTo:'company',pathMatch:'full'},
  {path:'login',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'company',component:SearchCompanyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {  
 }
