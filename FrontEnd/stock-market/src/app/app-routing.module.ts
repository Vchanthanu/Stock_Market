import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddCompanyComponent } from './company/add-company/add-company.component';
import { DetailCompanyComponent } from './company/detail-company/detail-company.component';
import { SearchCompanyComponent } from './company/search-company/search-company.component';
import { AuthGuard } from './service/auth.guard';
import { LoginComponent } from './site/login/login.component';
import { SignupComponent } from './site/signup/signup.component';

const routes: Routes = [
  {path:'',redirectTo:'company',pathMatch:'full'},
  {path:'login',component:LoginComponent},
  {path:'signup',component:SignupComponent},
  {path:'company',component:SearchCompanyComponent,canActivate:[AuthGuard]},
  {path:'addCompany',component:AddCompanyComponent,canActivate:[AuthGuard]},
  {path:'detail',component:DetailCompanyComponent,canActivate:[AuthGuard]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {  
 }
