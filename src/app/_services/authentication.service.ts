import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
//import { Http, RequestOptions, Headers } from '@angular/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

import { HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}
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
        map((token) => {
          if (token) {
            localStorage.setItem('token', JSON.stringify(token));
          }
          return token;
        })
      );
  }
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('dataUser');
  }
}
