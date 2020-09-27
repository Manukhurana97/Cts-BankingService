import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import {HttpClientModule} from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing';
import { FormBuilder, Validators } from '@angular/forms';
import { AppComponent } from '../../app.component';
import { UserService } from '../../Services/user.service';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let testBedAuthService: UserService;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ LoginComponent ],
      providers: [FormBuilder, AppComponent, UserService]
    })
    .compileComponents();

    testBedAuthService = TestBed.get(UserService)
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Login testing', () => {
    expect(testBedAuthService instanceof UserService).toBeTruthy();
    expect(component).toBeTruthy();
  });
});
