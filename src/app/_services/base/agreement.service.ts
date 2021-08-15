import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { AlertService } from '../base/alert.service';

@Injectable({
  providedIn: 'root',
})
export class AgreementService {
  constructor(private apiService: ApiBaseService) {}
  uploadShape(file: File) {
    const fd = new FormData();
    fd.append('file', file, file.name);
    return this.apiService.post(
      `${environment.apiUrl}/conservationagreement/upload`,
      fd
    );
  }
}
