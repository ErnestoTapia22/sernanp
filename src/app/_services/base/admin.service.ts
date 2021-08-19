import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private segmentList;
  private segmentInsert;
  private segmentSearch;
  constructor(
    private apiBaseService: ApiBaseService,
    private http: HttpClient
  ) {
    this.segmentList = '/list';
    this.segmentInsert = '/save';
  }
  moduleList(segment): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}/${segment}${this.segmentList}`
    );
  }
  moduleRegister(segment, item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}/${segment}${this.segmentInsert}`,
      item
    );

    // let headers = {
    //   'Content-Type': 'application/json',
    // }
    // // prettier-ignore
    // const testdata={
    //   item : {
    //   "name": "ernesto 5",
    //   "description": "tapia 5",
    //   "state": true
    // }
    // }
    // return this.http.post<any>(
    //   `http://localhost:8050/simrac/api/economicactivity/save2`,
    //   // `${environment.apiUrl}/${segment}${this.segmentInsert}`,
    //   'sadasdas'
    // );
  }
}
