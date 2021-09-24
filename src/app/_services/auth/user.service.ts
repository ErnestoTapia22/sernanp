import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../_models/auth/user';
import { environment } from '../../../environments/environment';
import { map } from 'rxjs/operators';
import { ApiBaseService } from '../base/api-base.service';
import { lastValueFrom, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private segmentModuleList;
  private segmentRoleInsert;
  private segmentRoleList;
  private segmentRoleDelete;
  private segmentModuleSearchByRol;
  private segmentRolModuleInsert;
  private segmentSearchUser;
  private segmentUserSave;
  private segmentUserSearchPrimary;
  private segmentUserDelete;
  constructor(
    private http: HttpClient,
    private apiBaseService: ApiBaseService
  ) {
    this.segmentModuleList = '/module/searchbysystem/';
    this.segmentRoleInsert = '/role/save';
    this.segmentRoleList = '/role/search/';
    this.segmentRoleDelete = '/role/delete/';
    this.segmentModuleSearchByRol = '/module/searchbyrole/';
    this.segmentRolModuleInsert = '/role/save2';
    this.segmentSearchUser = '/user/searchwithoutlogin/';
    this.segmentUserSave = '/user/save';
    this.segmentUserSearchPrimary = '/user/search';
    this.segmentUserDelete = '/user/delete/';
  }
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
  moduleList(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentModuleList}${id}`
    );
  }
  roleInsert(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}${this.segmentRoleInsert}`,
      item,
      null
    );
  }
  rolesList(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentRoleList}${id}`
    );
  }
  roleDelete(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentRoleDelete}${id}`
    );
  }
  moduleSearchByRol(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentModuleSearchByRol}${id}`
    );
  }
  rolModuleInsert(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}${this.segmentRolModuleInsert}`,
      item,
      null
    );
  }
  userSearch(documentNumber, system): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentSearchUser}${documentNumber}/${system}`
    );
  }
  userSave(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}/${this.segmentUserSave}`,
      item,
      null
    );
  }
  userSearchPrimary(item): Observable<any> {
    return this.apiBaseService.postFormData(
      `${environment.apiUrl}${this.segmentUserSearchPrimary}`,
      item
    );
  }
  userDelete(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentUserDelete}${id}`
    );
  }
}
