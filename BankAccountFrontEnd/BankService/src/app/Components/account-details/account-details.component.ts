import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from '../../Services/account.service';

@Component({
  selector: 'app-account-details',
  templateUrl: './account-details.component.html',
  styleUrls: ['./account-details.component.css']
})
export class AccountDetailsComponent implements OnInit {

  data: any = [];

  constructor(private route: Router, private router: ActivatedRoute, private accountService: AccountService) { }

  ngOnInit(): void {
    this.Accountdata();
  }

  // tslint:disable-next-line:typedef
  createaccount() {
    this.route.navigate(['/CreateAccount']);
  }

  // tslint:disable-next-line:typedef
  Accountdata(){
    const accno = this.router.snapshot.params.accountno;
    this.accountService.getUserAccountInfo(accno).subscribe(
      // tslint:disable-next-line:variable-name
      account_Data => {
        this.data = account_Data;
        console.log(account_Data);
      },
      error => {console.log(error)}
    );
  }

  // tslint:disable-next-line:typedef
  purhasefunds() {
    this.route.navigate(['/CreateAccount']);
  }

}
