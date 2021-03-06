import { Injectable } from '@angular/core';
import { lastValueFrom, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiBaseService } from '../base/api-base.service';

@Injectable({
  providedIn: 'root',
})
export class MonitoringService {
  private segmentList;
  private segmentSearch;

  constructor(private apiService: ApiBaseService) {
    this.segmentList = '/conservationagreement/list';
    this.segmentSearch = '/conservationagreement/search';
  }
  agreementList(): Observable<any> {
    return this.apiService.get(`${environment.apiUrl}${this.segmentList}`);
  }
  agreementSearch(item): Observable<any> {
    return this.apiService.postFormData(
      `${environment.apiUrl}${this.segmentSearch}`,
      item
    );
  }

  async agreementMonitoringList(): Promise<any> {
    const agreement$ = this.agreementList();
    return await lastValueFrom(agreement$);
  }
}
