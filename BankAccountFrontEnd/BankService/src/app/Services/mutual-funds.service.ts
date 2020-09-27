import { Injectable } from '@angular/core';
import { UserService } from './user.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MutualFundsService {


  MutualFundsServiceurl = 'http://localhost:8762/MutualFunds/Order/';
  TempFundsServiceurl = 'http://localhost:8762/MutualFunds/Select/';
  AllFundsServiceurl = 'http://localhost:8762/MutualFunds/Funds/';


  private token: any;

  constructor(private http: HttpClient, private userservice: UserService, private router: Router) {
  }


  getAllUserFunds(): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.MutualFundsServiceurl + `orderedFunds`, { headers: header });
  }


  getMutualFundInfo(Pan: any, Id: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.post(this.MutualFundsServiceurl + `{Pan}/{Id}`, { headers: header });
  }

  //  get all funds info
  getAllfunds(): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.AllFundsServiceurl + `All-Funds`, { headers: header });
  }

  //  add Mutualfunds to cart
  getaddfunds(FundId: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.TempFundsServiceurl + `MutualFund/` + FundId, { headers: header });
  }

  getaddAccount(AccNo: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.TempFundsServiceurl + `Account/` + AccNo, { headers: header });
  }

// Cart data
  getcartdata(): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.TempFundsServiceurl + `UserCartData/` , { headers: header });
  }

  cleancart(cartid: any): Observable<any> {
    if (this.token === undefined)
    {
      this.router.navigate(['/Login']);
    }

    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.TempFundsServiceurl + `RemoveFund/` + cartid, { headers: header });
  }

// purchase fund
  getpurchaseFunds(AccNo: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.MutualFundsServiceurl + `Purchasefunds/` + AccNo, { headers: header });
  }

  getpurchasedFundsdetail(Id: any): Observable<any> {

    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.MutualFundsServiceurl + `FundDetails/` +  Id, { headers: header });
  }

  getMutualFundsDelete(Id: any): Observable<any> {
    this.token = this.userservice.getToken();
    if (this.token === undefined) {
      this.router.navigate(['/Login']);
    }
    const header = new HttpHeaders().set('Authentication', this.token);
    return this.http.get(this.MutualFundsServiceurl + `Delete/` +  Id, { headers: header });
  }

}
