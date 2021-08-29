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
import { debug } from 'console';

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
    if (
      JSON.parse(localStorage.getItem('auth')) != null &&
      JSON.parse(localStorage.getItem('auth')) != undefined
    ) {
      this.token = JSON.parse(localStorage.getItem('auth')).access_token;
    }

    this.headers = this.getHttpHeaders();
  }
  get(url: string, params?: any, auth?: boolean): Observable<any> {
    return this.http
      .get<any>(url, {
        params: params,
        headers: auth
          ? this.getHttpHeaders(null, null, true)
          : new HttpHeaders(),
      })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }

  post(
    url: string,
    body: object = {},
    encodedCredentials?: string
  ): Observable<any> {
    let parsedBody;
    let parsedHeaders;
    if (
      encodedCredentials !== undefined &&
      encodedCredentials !== null &&
      encodedCredentials !== ''
    ) {
      parsedHeaders = this.getHttpHeaders('auth', encodedCredentials);
      parsedBody = 'grant_type=client_credentials';
    } else {
      this.headers = this.getHttpHeaders();
      parsedBody = body;
      parsedHeaders = this.getHttpHeaders();
    }

    return this.http
      .post(url, parsedBody, { headers: parsedHeaders })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  put(url: string, body: object = {}): Observable<any> {
    return this.http
      .put(url, body, { headers: this.headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  postFormData(
    url: string,
    body: object = {},
    auth?: boolean
  ): Observable<any> {
    let formdata = new FormData();
    const headers: any = this.getHttpHeaders('formdata');

    for (let key of Object.keys(body)) {
      formdata.append(key, body[key]);
    }
    console.log(formdata.get('item'));
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
  private getHttpHeaders(
    type?: string,
    encodedCredentials?: string,
    requireAuth?: boolean
  ): HttpHeaders {
    const httpOptions = { headers: new HttpHeaders() };

    switch (type) {
      case 'formdata':
        httpOptions.headers = httpOptions.headers.set(
          'Content-Type',
          'application/json'
        );
        httpOptions.headers = httpOptions.headers.set(
          'Accept',
          'application/json'
        );
        break;
      case 'auth':
        httpOptions.headers = httpOptions.headers.set(
          'Content-Type',
          'application/x-www-form-urlencoded'
        );
        httpOptions.headers = httpOptions.headers.set(
          'Authorization',
          `Basic ${encodedCredentials}`
        );
        break;
      case '':
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
    if (requireAuth) {
      if (this.token) {
        httpOptions.headers = httpOptions.headers.set(
          'Authorization',
          `Bearer ${this.token}`
        );
      }
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
