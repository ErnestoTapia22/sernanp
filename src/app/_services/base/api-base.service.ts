import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { AlertService } from '../../_services/base/alert.service';

import {
  HttpHeaders,
  HttpParams,
  HttpClient,
  HttpResponse,
} from '@angular/common/http';
import { NgxSpinnerService } from 'ngx-spinner';

@Injectable({
  providedIn: 'root',
})
export class ApiBaseService {
  // private segmentAPI = '/api/v2/';
  // private environment_api = `${environment.apiUrl}${this.segmentAPI}`;
  private token: any;

  private headers;
  constructor(
    private http: HttpClient,
    private spinner: NgxSpinnerService,
    private alertService: AlertService
  ) {
    if (JSON.parse(localStorage.getItem('token')) != null) {
      this.token = JSON.parse(localStorage.getItem('token')).access_token;
    }

    this.headers = this.getHttpHeaders();
  }
  get(url: string, params?: any): Observable<any> {
    return this.http
      .get<any>(url, { params: params })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }

  post(url: string, body: object = {}): Observable<any> {
    return this.http
      .post(url, body)
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  put(url: string, body: object = {}): Observable<any> {
    return this.http
      .put(url, body, { headers: this.headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  postFormData(url: string, body: object = {}): Observable<any> {
    const formdata = new FormData();
    const headers: any = this.getHttpHeaders('formdata');
    for (const key in body) {
      formdata.append(key, body[key]);
    }
    return this.http
      .post(url, formdata, { headers: headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  delete(url: string): Observable<any> {
    return this.http
      .delete<any>(url, { headers: this.headers })
      .pipe(catchError(catchError(this.formatError)))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  private getHttpHeaders(type?: string): HttpHeaders {
    const httpOptions = { headers: new HttpHeaders() };
    switch (type) {
      case 'formdata':
        // httpOptions.headers = httpOptions.headers.set(
        //   'Content-Type',
        //   'multipart/form-data'
        // );
        break;
      default:
        httpOptions.headers = httpOptions.headers.set(
          'Content-Type',
          'application/json'
        );
        httpOptions.headers = httpOptions.headers.set(
          'Accept',
          'application/json'
        );
        break;
    }
    if (this.token) {
      httpOptions.headers = httpOptions.headers.set(
        'Authorization',
        `Bearer ${this.token}`
      );
    }
    return httpOptions.headers;
  }
  private getErrorProperties(error) {
    const { status } = error;
    const { errors, fields } = error;
    return {
      status: status,
      errors: errors || error,
      fieldsError: fields,
    };
  }
  private formatError = (error) =>
    throwError(() => {
      const errorProperties = JSON.stringify(this.getErrorProperties(error));
      this.spinner.hide();
      this.alertService.error(
        'error en el api service: ' + errorProperties,
        'error'
      );
      new Error(errorProperties);
    });
}
