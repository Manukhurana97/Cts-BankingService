import { TestBed } from '@angular/core/testing';
import {HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { Test } from 'tslint';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { HttpClient } from '@angular/common/http';

describe('UserService', () => {
  let service: UserService;

  TestBed.configureTestingModule({
      providers: [
        UserService, HttpClient, HttpTestingController
      ],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    });

    // service= TestBed.get(UserService);

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UserService);
  });

  // it('should be created', () => {
  //   expect(service).toBeTruthy();
  // });
});
