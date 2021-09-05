import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class MasterPlanService {
  segmentDetail: string = '';
  segmentObjectiveList: string = '';
  segmentObjetiveInsert: string = '';
  segmentActionLineList: string = '';
  segmentActionLineInsert: string = '';
  segmentActionLineDelete: string = '';

  constructor(private apiBaseService: ApiBaseService) {
    this.segmentDetail = '/masterplan/searchbyanp/';
    this.segmentObjectiveList = '/masterplan/searchobjetives/';
    this.segmentObjetiveInsert = '/objetive/save';
    this.segmentActionLineList = '/objetive/searchactionlines/';
    this.segmentActionLineInsert = '/actionline/save';
    this.segmentActionLineDelete = '/actionline/delete/';
  }
  masterPlanDetailByAnp(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentDetail}${id}`
    );
  }
  masterPlanObjetiveList(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentObjectiveList}${id}`
    );
  }
  goalsInsert(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}${this.segmentObjetiveInsert}`,
      item,
      null
    );
  }
  actionLineList(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentActionLineList}${id}`
    );
  }
  actionLineInsert(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}${this.segmentActionLineInsert}`,
      item,
      null
    );
  }
  actionLineDelete(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentActionLineDelete}${id}`
    );
  }
}
