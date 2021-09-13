import { Injectable } from '@angular/core';
import { ApiBaseService } from '../../base/api-base.service';
import { environment } from '../../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnpService {
  segmentSearch: String = '';
  constructor(private apiBaseService: ApiBaseService) {
    this.segmentSearch = '/anp/search';
  }
  anpSearch(item): Observable<any> {
    return this.apiBaseService.postFormData(
      `${environment.apiUrl}${this.segmentSearch}`,
      item
    );
  }
}
