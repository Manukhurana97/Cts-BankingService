import { Injectable } from '@angular/core';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserService } from './user.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  public AccountServiceurl = 'http://localhost:8762/AccountDetails/';
  private token: any;

  constructor(private http: HttpClient, private userservice: UserService, private router: Router) {
  }

  CreateAccount(account: Account): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.post(this.AccountServiceurl + `Add-Account`, account,  {headers: header});
  }

  getAllUserAccount(): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.AccountServiceurl + `AllAccount` , {headers: header});
  }

  getUserAccountInfo(accountno: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.AccountServiceurl + `AccountNo/` + accountno,  {headers: header});
  }







}
