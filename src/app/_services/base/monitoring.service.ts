import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { AlertService } from '../base/alert.service';

@Injectable({
  providedIn: 'root',
})
export class MonitoringService {
  private segmentList;

  constructor(private apiService: ApiBaseService) {
    this.segmentList = '/conservationagreement/list';
  }
  agreementList(): Observable<any> {
    return this.apiService.get(`${environment.apiUrl}${this.segmentList}`);
  }
  async agreementMonitoringList(): Promise<any> {
    const agreement$ = this.agreementList();
    return await lastValueFrom(agreement$);
  }
}
