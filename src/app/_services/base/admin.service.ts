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
  private segmentDelete;
  private segmentDetail;
  private segmentUpdate;
  constructor(
    private apiBaseService: ApiBaseService,
    private http: HttpClient
  ) {
    this.segmentList = '/list';
    this.segmentInsert = '/save';
    this.segmentDelete = '/delete';
    this.segmentDetail = '/detail';
    this.segmentUpdate = '/save';
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
  }
  moduleDelete(segment, id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}/${segment}${this.segmentDelete}/${id}`
    );
  }
  moduleDetail(segment, id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}/${segment}${this.segmentDetail}/${id}`
    );
  }
  moduleUpdate(segment, item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}/${segment}${this.segmentUpdate}`,
      item
    );
  }
}
