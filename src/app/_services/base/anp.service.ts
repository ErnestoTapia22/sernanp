import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnpService {
  segmentList: String = '';
  constructor(private apiBaseService: ApiBaseService) {}
  agreementList(): Observable<any> {
    return this.apiBaseService.get(
      `${environment.externalServices[0].agreement[0]}`
    );
  }
}
