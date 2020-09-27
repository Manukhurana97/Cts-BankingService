import { Component, OnInit } from '@angular/core';
import { UserService } from '../../Services/user.service';
import { Router } from '@angular/router';
import {AppComponent} from "../../app.component" 

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  status: boolean;

  constructor(private userservice: UserService, private router: Router, private app: AppComponent) 
  {
  }

  ngOnInit(): void 
  {
    
    this.status = false
    if (this.userservice.isAuthenticated()) {
      this.status = true;
    }
  }

  onlogout()
  {
    this.userservice.removeToken();
    this.app.refresh()
    this.router.navigate(['/Login '])
    
  }

}
