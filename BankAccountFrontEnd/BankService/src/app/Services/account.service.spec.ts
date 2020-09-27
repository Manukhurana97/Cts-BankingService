import {TestBed} from '@angular/core/testing';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {AccountService} from './account.service';
import {NO_ERRORS_SCHEMA} from '@angular/core';
import {Account} from 'src/app/Model/Account.model ';

describe('AccountService', () => {

  let service: AccountService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      // providers: [HttpClient, HttpTestingController, AccountService],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    });

    service = TestBed.get(AccountService);
    httpMock = TestBed.get(HttpTestingController);
  });

  afterEach(() => httpMock.verify());


  // all user account
  it('All Account  API Testing', () => {

    const testdata: Account[] = [
      {accountno: 1234567890, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici'},
      {accountno: 1234567891, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici'}
    ];

    service.getAllUserAccount().subscribe( data => {
      console.log(data);
      // expect(data.status).toBe('200');
      expect(data).toBe(testdata);
    });

    const request = httpMock.expectOne(service.AccountServiceurl + `AllAccount`);
    expect(request.request.method).toBe('GET');

    request.flush(testdata);
  });


 // account in info
  it('User Account Details API Testing', () => {

    const testdata: Account[] = [
      {accountno: 1234567890, ifsc: 'abc123', micr: '1234', amount: 10000, bankname: 'icici'}
    ];

    service.getUserAccountInfo(1234512345).subscribe( data => {
      expect(data).toBe(testdata);
    });

    const request = httpMock.expectOne(service.AccountServiceurl + `AccountNo/1234512345`);
    expect(request.request.method).toBe('GET');

    request.flush(testdata);
  });
});
