import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AccountsComponent } from './accounts.component';
import { AccountService } from 'src/app/Services/account.service';
import {Account} from 'src/app/Model/Account.model ';
import { LoginComponent } from '../login/login.component';

describe('AccountsComponent', () => {
  let component: AccountsComponent;
  let fixture: ComponentFixture<AccountsComponent>;
  let dataservice: AccountService;
  let testctrl: HttpTestingController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpClientModule, RouterTestingModule.withRoutes([])],
      declarations: [ AccountsComponent ],
      providers: [HttpClient, HttpTestingController]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  beforeEach(() => {
     dataservice = TestBed.get(dataservice);
     testctrl = TestBed.get(HttpTestingController);
  });

  afterEach(() => {
    testctrl.verify();
  });


  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });


});
