import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './Components/login/login.component';
import { RegisterComponent } from './Components/register/register.component';
import { HeaderComponent } from './Components/header/header.component';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AccountsComponent } from './Components/accounts/accounts.component';
import { CreateAccountComponent } from './Components/create-account/create-account.component';
import { MutualFundsComponent } from './Components/mutual-funds/mutual-funds.component';
import { AccountDetailsComponent } from './Components/account-details/account-details.component';
import { PurchaseFundsComponent } from './Components/purchase-funds/purchase-funds.component';
import { PurchaseFundsDetailsComponent } from './Components/purchase-funds-details/purchase-funds-details.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    AccountsComponent,
    CreateAccountComponent,
    MutualFundsComponent,
    AccountDetailsComponent,
    PurchaseFundsComponent,
    PurchaseFundsDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
