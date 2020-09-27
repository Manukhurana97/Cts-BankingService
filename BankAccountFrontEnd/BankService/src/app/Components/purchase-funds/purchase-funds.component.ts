import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../Services/account.service';
import { MutualFundsService } from '../../Services/mutual-funds.service';


@Component({
  selector: 'app-purchase-funds',
  templateUrl: './purchase-funds.component.html',
  styleUrls: ['./purchase-funds.component.css']
})
export class PurchaseFundsComponent implements OnInit {

  accountdata: any = [];
  Funddata: any = [];
  cartdata: any = [];
  showaccount: boolean;
  MF_alert = false;
  mgs: string;
  s_mgs: string;
  error_alert = false;

  constructor(private route: Router, private accountService: AccountService, private mutualFundsService: MutualFundsService,
              private router: Router) { }

  ngOnInit(): void {
    this.AllFunds();
    this.Account();
    this.Cart();

  }

// all funds data
  AllFunds() {
    this.mutualFundsService.getAllfunds( ).subscribe(
      fund_data => {
        this.Funddata = fund_data.list;
        console.log(this.Funddata);
      },
      error => {
        console.log(error);
      }
    );
  }


  // select funds
  async P_Funds(fundid) {
    this.mutualFundsService.getaddfunds(fundid ).subscribe(
      fund_data => {
        this.mgs = fund_data.mgs;
        console.log(fund_data);
        this.s_mgs = 'Please select the account';
        console.log(this.mgs); },
      error => {
        console.log(error);
      },

    );
    this.MF_alert = false;
    this.MF_alert = true;
    await this.delay(1000);
    this.MF_alert = false;
    this.showaccount = true;

  }

// all Accounts
  Account() {

    this.accountService.getAllUserAccount().subscribe(
      accountdata => {
        if (accountdata.status == 200) {
          this.accountdata = accountdata.lst;
        }
      },
      error => {
        console.log(error);
      },

    );
  }

  // select account
  async s_Account(AccId) {

    this.mutualFundsService.getaddAccount(AccId).subscribe(
      async Account_data => {
        console.log(Account_data  );
        this.MF_alert = false;
        this.MF_alert = true;
        this.mgs = Account_data.mgs;
        await this.delay(1000);
        this.MF_alert = false;
        window.location.reload();
      },
      async error => {
        console.log(error);
        this.MF_alert = false;
        this.error_alert = false;
        this.error_alert = true;
        this.mgs = 'Please select the Mutual Funds First';
        await this.delay(2000);
        this.error_alert = false;
      }
    );

    this.Cart();
  }

  // cart data
  Cart() {
    this.mutualFundsService.getcartdata().subscribe(
      cart_data => {
        this.cartdata = cart_data.temp;
        console.log(cart_data);
        },

      error => {
        console.log(error);
      }
    );
    this.showaccount = true;

  }

  //  clear cart
  clearCart(cartid) {
    this.mutualFundsService.cleancart(cartid).subscribe(
      cart_data => {
        console.log(cart_data); },

      error => {
        console.log(error);
      }
    );
    this.ngOnInit();
    window.location.reload();
  }

  //  purchase funds
  Buy(Accno) {
    this.mutualFundsService.getpurchaseFunds(Accno).subscribe(
      // tslint:disable-next-line:variable-name
      purchase_funds => {
        this.router.navigate(['/Login']);

      },
      error => {
        console.log(error);
      }
    );
    this.showaccount = true;
  }


  // tslint:disable-next-line:typedef
   delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
}



}
