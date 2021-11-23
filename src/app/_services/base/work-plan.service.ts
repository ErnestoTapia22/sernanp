import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { AlertService } from '../base/alert.service';

@Injectable({
  providedIn: 'root',
})
export class WorkPlanService {
  private segmentWorkPlanInsert;
  private segmentActivityListByCommitment;
  private segmentMonitoringSearch;
  private segmentMonitoringSearchHistory;
  private segmentWorkPlanSearchHistory;
  private segmentMonitoringInsert;
  private segmentWorkPlanGetDetail;
  constructor(private apiService: ApiBaseService) {
    this.segmentWorkPlanInsert = '/workplan/save';
    this.segmentActivityListByCommitment = '/activity/searchbycommitment/';
    this.segmentMonitoringSearch = '/workplan/searchbyagreement/';
    this.segmentMonitoringSearchHistory = '/monitoring/searchbyagreement/';
    this.segmentWorkPlanSearchHistory = '/workplan/search/';
    this.segmentMonitoringInsert = '/monitoring/save';
    this.segmentWorkPlanGetDetail = '/monitoring/workPlanDetail/';
  }
  workPlanInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentWorkPlanInsert}`,
      item,
      null
    );
  }

  activityListByCommitment(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentActivityListByCommitment}${id}`
    );
  }
  monitoringSearch(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentMonitoringSearch}${id}`
    );
  }
  monitoringSearchHistory(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentMonitoringSearchHistory}${id}`
    );
  }
  monitoringInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentMonitoringInsert}`,
      item,
      null
    );
  }
  searchWorkPlanHistory(id: string): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentWorkPlanSearchHistory}${id}`
    );
  }
  getWorkPlanDetail(id) {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentWorkPlanGetDetail}${id}`
    );
  }
}
