import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderComponent } from './header.component';
import {HttpClient, HttpClientModule, HttpHandler} from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing'
import { RouterTestingModule } from '@angular/router/testing';
import {AppComponent} from "../../app.component"
import { LoginComponent } from '../login/login.component';
import { Component, NgModule } from '@angular/core';
import { Router } from '@angular/router';
import { AccountsComponent } from '../accounts/accounts.component';

describe('HeaderComponent', () => {
  let component: HeaderComponent;
  let fixture: ComponentFixture<HeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      
      imports: [RouterTestingModule.withRoutes([{path: 'Login', component:LoginComponent}])],
      declarations: [],
      providers: [HttpClient, HttpClientModule, HttpHandler]


    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // it('should create', () => {
  //   expect(component).toBeTruthy();
  // });
});
