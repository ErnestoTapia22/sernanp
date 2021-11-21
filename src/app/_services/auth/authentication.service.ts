import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
//import { Http, RequestOptions, Headers } from '@angular/http';
import { environment } from '../../../environments/environment';
import { map } from 'rxjs/operators';
import { Observable, BehaviorSubject } from 'rxjs';

import { HttpParams } from '@angular/common/http';
import { User } from '../../_models/auth/user';
import { ApiBaseService } from '@app/_services/base/api-base.service';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private userSubject: BehaviorSubject<User>;
  private segmentUserValidate: string = '';
  public user: Observable<User>;
  constructor(
    private http: HttpClient,
    private router: Router,
    private apiBaseService: ApiBaseService
  ) {
    this.userSubject = new BehaviorSubject<User>(
      JSON.parse(localStorage.getItem('user'))
    );

    this.user = this.userSubject.asObservable();
    this.segmentUserValidate = '/user/validate/';
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
  authenticate(token) {
    try {
      this.apiBaseService
        .get(`${environment.apiUrl}${this.segmentUserValidate}${token}`)
        .subscribe((response) => {
          console.log(response);
          if (
            response &&
            response.item !== undefined &&
            response.item !== null
          ) {
            localStorage.setItem('user', JSON.stringify(response.item));
            this.userSubject.next(response.item as User);
            console.log(this.userSubject.value);
            this.router.navigate(['/map/index']);
          } else {
            this.logout();
          }
        });
    } catch (error) {
      this.logout();
    }
  }

  logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('dataUser');
    localStorage.removeItem('auth');
    this.userSubject.next(null);
    if (environment.production) {
      window.location.href = environment.logOutUrl;
    } else {
      this.router.navigate([environment.logOutUrl]);
    }
  }
  getNotifications(): Observable<any> {
    return this.apiBaseService.get(`${environment.apiUrl}/getNotifications`);
  }
}
