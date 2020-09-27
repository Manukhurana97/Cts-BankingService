import { Injectable, Inject } from '@angular/core';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
import { HttpClient, HttpHandler, HttpHeaders } from '@angular/common/http';
import { User } from '../Model/login.model';
import { Register } from '../Model/Register.model';
import { Observable } from 'rxjs';
import {SESSION_STORAGE, StorageService} from 'ngx-webstorage-service';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userServiceurl = 'http://localhost:8762/UserDetails/'

  constructor(@Inject(SESSION_STORAGE) private storage: StorageService,private http: HttpClient) {
  }

  userLogin(user: User): Observable<any> {
    
    return this.http.post(this.userServiceurl+ `Login`, user);
  }

  userRegister(register: Register): Observable<any> {
   
    return this.http.post(this.userServiceurl+ `Signup`, register);
  }



  storeToken(token: string,) {
    this.storage.set('SessionID', token);
  }

  getToken() {
    return this.storage.get('SessionID');
  }

  removeToken() {
    this.storage.remove('auth_type');
    return this.storage.remove('SessionID');
  }

  public isAuthenticated(): boolean {
    // tslint:disable-next-line:triple-equals
    if (this.getToken() != 'undefined' && this.getToken() != null) {
      return true;
    }
    return false;
  }

}