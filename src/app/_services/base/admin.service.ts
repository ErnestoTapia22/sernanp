import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  private segmentList;
  private segmentSearch;
  constructor(private apiBaseService: ApiBaseService) {
    this.segmentList = '/list';
  }
  moduleList(segment): Observable<any> {
    return this.apiBaseService.get(
      `${environment.apiUrl}/${segment}${this.segmentList}`
    );
  }
}
