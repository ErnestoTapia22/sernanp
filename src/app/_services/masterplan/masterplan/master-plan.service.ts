import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ApiBaseService } from '../../base/api-base.service';

@Injectable({
  providedIn: 'root',
})
export class MasterPlanService {
  segmentDetail: string = '';
  segmentObjectiveList: string = '';
  segmentObjetiveInsert: string = '';
  segmentObjetiveDelete: string = '';
  segmentActionLineList: string = '';
  segmentActionLineInsert: string = '';
  segmentActionLineDelete: string = '';
  segmentMasterPlanInsert: string = '';

  constructor(private apiBaseService: ApiBaseService) {
    this.segmentDetail = '/masterplan/searchbyanp/';
    this.segmentObjectiveList = '/masterplan/searchobjetives/';
    this.segmentObjetiveInsert = '/objetive/save';
    this.segmentObjetiveDelete = '/objetive/delete/';
    this.segmentActionLineList = '/objetive/searchactionlines/';
    this.segmentActionLineInsert = '/actionline/save';
    this.segmentActionLineDelete = '/actionline/delete/';
    this.segmentMasterPlanInsert = '/masterplan/save';
  }
  masterPlanInsert(item): Observable<any> {
    return this.apiBaseService.post(
      `${environment.apiUrl}${this.segmentMasterPlanInsert}`,
      item,
      null
    );
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
  commitmentDelete(id): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}${this.segmentObjetiveDelete}${id}`
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
  componentList(): Observable<any> {
    return this.apiBaseService.get(`${environment.apiUrl}/component/list`);
  }
}
