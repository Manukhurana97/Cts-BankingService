import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../Services/user.service';
import { AccountService } from '../../Services/account.service';


@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {

  public accountForm: any;
  error = false;
  message = String;
  MF_alert: boolean=false;
  mgs: string;
  s_mgs: string;
  error_mgs: boolean=false;

  constructor(private account : AccountService, private userService: UserService ,private route: Router, private formBuilder: FormBuilder) {}

  ngOnInit(): void {

    if (!this.userService.isAuthenticated()) {
      this.route.navigate(['/']);
    }
  
    this.accountForm = this.formBuilder.group({
      accountno: ['1234512345', Validators.required],
      ifsc: ['abc123', Validators.required],
      micr: ['1234', Validators.required],
      amount: ['10000.00', Validators.required],
      bankname: ['ICICI', Validators.required],
    })
  
  }

  CreateAccount(): void {
    this.account.CreateAccount(this.accountForm.value).subscribe(
      async userdata => {
        
        if (userdata.status === 201) {
          
          this.MF_alert=false;
          this.MF_alert=true;
          this.mgs = userdata.message;
          await this.delay(2000);
          this.MF_alert=false;
         this.route.navigate(['/']);
        }
      },
      async error1 => {
        this.MF_alert=false;
        this.error_mgs=false;
        this.error_mgs=true;
        this.mgs = error1.error.message  
        await this.delay(2000);
        this.error_mgs=false;
      }
    );
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  closealert()
  {
    this.MF_alert=false;
    this.MF_alert=false
  }

}
