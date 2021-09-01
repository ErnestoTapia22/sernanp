import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
//import { Http, RequestOptions, Headers } from '@angular/http';
import { environment } from '../../../environments/environment';
import { map } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';

import { HttpParams } from '@angular/common/http';
import { User } from '../../_models/auth/user';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private userSubject: BehaviorSubject<User>;
  public user: Observable<User>;
  constructor(private http: HttpClient, private router: Router) {
    this.userSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('user'))
    );
    this.user = this.userSubject.asObservable();
  }
  public get userValue(): User {
    return this.userSubject.value;
  }
  login(user: string, password: string) {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');
    const body = new HttpParams()
      .set('username', user)
      .set('password', password)
      .set('grant_type', 'password');

    //return this.http.post(`${environment.apiUrl}/api/v2/token`, body, { headers: headers })
    return this.http
      .post(`${environment.apiUrl}/Auth/token`, body, { headers: headers })
      .pipe(
        map((user) => {
          if (user) {
            localStorage.setItem('user', JSON.stringify(user));
            this.userSubject.next(user as User);
          }
          return user;
        })
      );
  }
  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('dataUser');
    localStorage.removeItem('auth');
    this.userSubject.next(null);
    this.router.navigate(['/map']);
  }
}
