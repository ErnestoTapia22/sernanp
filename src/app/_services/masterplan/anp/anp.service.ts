import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { ApiBaseService } from '../../base/api-base.service';

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
  anpList(): Observable<any> {
    return this.apiBaseService.get(`${environment.apiUrl}/anp/list`);
  }
}
