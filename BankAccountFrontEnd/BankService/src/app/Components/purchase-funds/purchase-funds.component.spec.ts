import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import {HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing';
import { PurchaseFundsComponent } from './purchase-funds.component';

describe('PurchaseFundsComponent', () => {
  let component: PurchaseFundsComponent;
  let fixture: ComponentFixture<PurchaseFundsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ PurchaseFundsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PurchaseFundsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Purchased funds', () => {
    expect(component).toBeTruthy();
  });
});
