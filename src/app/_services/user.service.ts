import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../_models/user';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}
  getUserData(token: string) {
    //let headers = new HttpHeaders();
    //headers = headers.set('Authorization', 'Bearer ' + token);
    //return this.http.get(`${environment.apiUrl}/api/v2/Account/GetUserData`, { headers: headers})
    return this.http.get(`${environment.apiUrl}/Account/GetUserData`).pipe(
      map((user) => {
        if (user) {
          localStorage.setItem('dataUser', JSON.stringify(user));
        }
        return user;
      })
    );
  }
}
