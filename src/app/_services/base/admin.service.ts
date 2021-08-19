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
    //return this.apiBaseService.post(
    //  `${environment.apiUrl}/${segment}${this.segmentInsert}`,
    //  item
    //);
     const testdata = {
       "name": "ernesto 5",
       "description": "tapia 5",
       "state": true
     };
     console.log(testdata);
     return this.http.post<any>(
       `http://localhost:8050/simrac/api/economicactivity/save`,
       // `${environment.apiUrl}/${segment}${this.segmentInsert}`,
       testdata
     );
     // return this.http.post<any>(
    //   `http://localhost:8050/simrac/api/economicactivity/save2`,
    //   // `${environment.apiUrl}/${segment}${this.segmentInsert}`,
    //   'sadasdas'
    // );
  }
}
