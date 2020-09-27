import { TestBed } from '@angular/core/testing';

import { MutualFundsService } from './mutual-funds.service';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {Funds} from 'src/app/Model/Funds.model ';
import {MutualFunds} from 'src/app/Model/MutualFunds.model ';
import {LoginComponent} from '../Components/login/login.component';
import {Account} from '../Model/Account.model ';


describe('MutualFundsService', () => {
  let purchasefundservice: MutualFundsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      // providers: [HttpClient, HttpTestingController, MutualFundsService],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    });
    purchasefundservice = TestBed.get(MutualFundsService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() => httpMock.verify());


  // Purchase Mutual Fund
  it('Testing Purchase Mutual Funds', () => {
    const testdata: Funds[] = [
      // tslint:disable-next-line:max-line-length
      {accountno: 1234567890, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici', id: 1, name: 'Aditya Birla', pan: 'AAAAA1111A'},
      // tslint:disable-next-line:max-line-length
      {accountno: 1234567891, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici', id: 1, name: 'Aditya Birla', pan: 'AAAAA1111A'}
    ];

    purchasefundservice.getAllUserFunds().subscribe( data => {
      console.log(data);
      expect(data).toBe(testdata);
    });

    const request = httpMock.expectOne(purchasefundservice.MutualFundsServiceurl + `orderedFunds`);
    expect(request.request.method).toBe('GET');

    request.flush(testdata);

  });


  // Funds
  it('Testing Mutual Funds details', () => {
    const MutualFunddata: MutualFunds[] = [

      { name: 'ICICI Prudential Mutual Fund', scheme_category: 'Income'},
      { name: 'ICICI Prudential Mutual Fund', scheme_category: 'Income'}
    ];

    purchasefundservice.getAllfunds().subscribe( data => {
      expect(data).toBe(MutualFunddata);
    });

    const request = httpMock.expectOne(purchasefundservice.AllFundsServiceurl + `All-Funds`);
    expect(request.request.method).toBe('GET');

    request.flush(MutualFunddata);

  });




  it('Cart Details API Testing', () => {

    const testdata: Account[] = [
      {accountno: 1234567890, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici'}
    ];

    purchasefundservice.getcartdata().subscribe( data => {
      expect(data).toBe(testdata);
    });

    const request = httpMock.expectOne(purchasefundservice.TempFundsServiceurl + `UserCartData/`);
    expect(request.request.method).toBe('GET');

    request.flush(testdata);
  });
});
