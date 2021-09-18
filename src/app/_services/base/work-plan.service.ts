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
  constructor(private apiService: ApiBaseService) {
    this.segmentWorkPlanInsert = '';
  }
  workPlanInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentWorkPlanInsert}`,
      item,
      null
    );
  }
}
