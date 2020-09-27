import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../Services/user.service';
import { AppComponent } from '../../app.component';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public loginForm: any;
  error = false;
  message = String;
  mgs: String;
  error_alert: boolean=false;
  

  constructor(private userService: UserService, private route: Router, private formBuilder: FormBuilder, private app: AppComponent) {
  }

  ngOnInit(): void {

    if (this.userService.isAuthenticated()) {
      this.route.navigate(['/']);
    }

    this.loginForm = this.formBuilder.group({
      email: ['Manukurana@gmail.com', Validators.required],
      password: ['Manu@123', Validators.required]
    });
  }

  login(): void {
    this.userService.userLogin(this.loginForm.value).subscribe(
      userdata => {
        
        if (userdata.status == 200) {
          this.userService.storeToken(userdata.token);
          this.app.refresh()
        } 
      },
      async error1 => {
        this.error_alert=false;
        this.error_alert=true;
        this.mgs = error1.error.message
        await this.delay(1000);
        this.error_alert=false;
              }
    );
  }
  delay(arg0: number) {
    throw new Error("Method not implemented.");
  }
}