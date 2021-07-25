import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import {
  HttpHeaders,
  HttpParams,
  HttpClient,
  HttpResponse,
} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ApiBaseService {
  private segmentAPI = '/api/v2/';
  private environment_api = `${environment.apiUrl}${this.segmentAPI}`;
  private token: any;

  private headers;
  constructor(private http: HttpClient) {
    if (JSON.parse(localStorage.getItem('token')) != null) {
      this.token = JSON.parse(localStorage.getItem('token')).access_token;
    }

    this.headers = this.getHttHeaders();
  }
  get(path: string, params?: any): Observable<any> {
    //return this.http.get<any>(`${this.enviroment_api}${path}`, { headers: this.headers, params: params })
    return this.http
      .get<any>(`${environment.apiUrl}/${path}`, { params: params })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }

  post(path: string, body: object = {}): Observable<any> {
    //return this.http.post(`${this.enviroment_api}${path}`, body, { headers: this.headers })
    return this.http
      .post(`${environment.apiUrl}/${path}`, body)
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  put(path: string, body: object = {}): Observable<any> {
    //return this.http.put(`${this.enviroment_api}${path}`, body, { headers: this.headers })
    return this.http
      .put(`${environment.apiUrl}/${path}`, body, { headers: this.headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  postFormData(path: string, body: object = {}): Observable<any> {
    const formdata = new FormData();
    const headers: any = this.getHttHeaders('formdata');
    for (const key in body) {
      formdata.append(key, body[key]);
    }
    //return this.http.post(`${this.enviroment_api}${path}`, formdata, { headers: headers })
    return this.http
      .post(`${environment.apiUrl}/${path}`, formdata, { headers: headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  delete(path: string): Observable<any> {
    //return this.http.delete<any>(`${this.enviroment_api}${path}`, { headers: this.headers })
    return this.http
      .delete<any>(`${environment.apiUrl}/${path}`, { headers: this.headers })
      .pipe(catchError(this.formatError))
      .pipe(map((res: HttpResponse<any>) => res));
  }
  private getHttHeaders(type?: string): HttpHeaders {
    const httpOptions = { headers: new HttpHeaders() };
    switch (type) {
      case 'formdata':
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
  private formatError = (error) => throwError(this.getErrorProperties(error));
}
