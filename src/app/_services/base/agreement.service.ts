import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { AlertService } from '../base/alert.service';
import {
  HttpHeaders,
  HttpParams,
  HttpClient,
  HttpResponse,
} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AgreementService {
  private segmentSearch;
  private segmentDetail;
  constructor(private apiService: ApiBaseService, private http: HttpClient) {
    this.segmentSearch = '/conservationagreement/search';
    this.segmentDetail = '/conservationagreement/detail';
  }
  uploadShape(file: File) {
    const fd = new FormData();
    fd.append('file', file, file.name);
    return this.apiService.post(
      `${environment.apiUrl}/conservationagreement/upload`,
      fd
    );
  }
  getServices(url: string): Observable<any> {
    return this.apiService.get(url, null, true);
  }
  agreementSearch(item): Observable<any> {
    return this.apiService.postFormData(
      `${environment.apiUrl}${this.segmentSearch}`,
      item
    );
  }
  agreementDetail(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentDetail}/${id}`
    );
  }
}
