import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { AccountsComponent } from './Components/accounts/accounts.component';
import { CreateAccountComponent } from './Components/create-account/create-account.component';
import { AccountDetailsComponent } from './Components/account-details/account-details.component';
import { PurchaseFundsComponent } from './Components/purchase-funds/purchase-funds.component';
import { PurchaseFundsDetailsComponent } from './Components/purchase-funds-details/purchase-funds-details.component';

const routes: Routes = [
  {path: '', component:AccountsComponent},
  {path: 'Login', component:LoginComponent},
  {path: 'Register', component:RegisterComponent},
  {path: 'CreateAccount', component:CreateAccountComponent},
  {path: 'AccountInfo/:accountno', component:AccountDetailsComponent},
  {path: 'Purchase funds', component: PurchaseFundsComponent},
  {path: 'FundDetails/:Id' , component: PurchaseFundsDetailsComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
