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
  constructor(private apiService: ApiBaseService) {
    this.segmentWorkPlanInsert = '/workplan/save';
    this.segmentActivityListByCommitment = '/activity/searchbycommitment/';
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
}
