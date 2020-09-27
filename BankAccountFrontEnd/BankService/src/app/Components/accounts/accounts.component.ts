import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../../Services/account.service';
import { MutualFundsService } from '../../Services/mutual-funds.service';
import { UserService } from '../../Services/user.service';



@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  accountdata: any = [];
  Funddata: any = [];
  message:any;


  constructor(private route: Router, private accountService: AccountService, private mutualFundsService: MutualFundsService,
            private userservise: UserService  ) { }

  ngOnInit(): void {
    this.Funds()
    this.Account()
    
  }

  createaccount() {
    this.route.navigate(['/CreateAccount']);
  }

  Account() {
    this.accountService.getAllUserAccount().subscribe(
      accountdata => {
        console.log("data", accountdata.status)
        
        if (accountdata.status == 200) {
          this.accountdata = accountdata.lst
        }
      },
      error => {
        console.log(error);
      }
    )
  }

  Funds() {
    this.mutualFundsService.getAllUserFunds().subscribe(
      fund_data => {
       
        this.Funddata = fund_data.o_list;
      },
      error => {
        console.log(error)
      }
    )
  }

  purhasefunds() {
    this.route.navigate(["/Purchase funds"])
  }

  funddetails(Id) {
    this.route.navigate(['/FundDetails', Id])
  }

  Accountdetails(accountno) {
    this.route.navigate(['/AccountInfo', accountno])
  }


}
