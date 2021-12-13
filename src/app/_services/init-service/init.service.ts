import { Injectable } from '@angular/core';
import { EMPTY, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiBaseService } from '../base/api-base.service';

@Injectable({
  providedIn: 'root',
})
export class InitService {
  constructor(private apiBaseService: ApiBaseService) {}

  getAuthorization(): Observable<any> {
    console.log('InitService.getAuthorization');
    if (
      environment.authUrl !== undefined &&
      environment.authCredentials !== undefined
    ) {
      return this.apiBaseService.post(
        `${environment.authUrl}`,
        {},
        btoa(`${environment.authCredentials}`)
      );
    } else {
      return EMPTY;
    }
  }
}
