import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PurchaseFundsDetailsComponent } from './purchase-funds-details.component';
import {HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from '../login/login.component';

describe('PurchaseFundsDetailsComponent', () => {
  let component: PurchaseFundsDetailsComponent;
  let fixture: ComponentFixture<PurchaseFundsDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, HttpClientModule, 
        RouterTestingModule.withRoutes(
          [{path: 'Login', component: LoginComponent}]
        )
      ],
      declarations: [ PurchaseFundsDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseFundsDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
