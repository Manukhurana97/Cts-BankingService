import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { UserService } from '../../Services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  public registerForm: any;
  error = false;
  message = String;
  MF_alert: boolean=false;
  mgs: string;
  s_mgs: string;
  error_alert: boolean=false;

  constructor(private userService: UserService, private route: Router, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {

    if (this.userService.isAuthenticated()) {
      this.route.navigate(['/']);
    }


    this.registerForm = this.formBuilder.group({
      email: ['@gmail.com', Validators.required],
      password: ['Manu@123', Validators.required],
      confirm_password: ['Manu@123', Validators.required],
      firstname: ['abc', Validators.required],
      lastname: ['abc', Validators.required],
      dob: ['01/01/1950', Validators.required],
      contactNo: ['1234567890', Validators.required],
      pan: ['', Validators.required],
    });
  }

  Register(): void {
    this.userService.userRegister(this.registerForm.value).subscribe(
      async userdata => {
        if (userdata.status === 201) {
         this.route.navigate(['/Login']);
        } 
      },
      async error1 => {
        console.log(error1)
        this.error_alert=false;
        this.error_alert=true;
        this.mgs=error1.error.message;
        await this.delay(2000);
        this.MF_alert=false;
      }
    );
  }


  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }



}

