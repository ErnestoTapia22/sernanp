import { Injectable } from '@angular/core';
import { ApiBaseService } from '../base/api-base.service';
import { environment } from '../../../environments/environment';
import { lastValueFrom, Observable, Subscription } from 'rxjs';
import { AlertService } from '../base/alert.service';
import {
  HttpHeaders,
  HttpParams,
  HttpClient,
  HttpResponse,
} from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AgreementService {
  private segmentSearch;
  private segmentDetail;
  private segmentAgreementStateList;
  private segmentAgreementSourceList;
  private segmentAgreementInsert;
  private segmentAlliedCategoryList;
  private segmentAlliedCategoryInsert;
  private segmentAlliedInsert;
  private segmentAlliedSearch;
  private segmentAlliedDelete;
  private segmentCommitmentsInsert;
  private segmentCommitmentsSearch;

  constructor(private apiService: ApiBaseService, private http: HttpClient) {
    this.segmentSearch = '/conservationagreement/search';
    this.segmentDetail = '/conservationagreement/detail';
    this.segmentAgreementStateList = '/agreementstate/list';
    this.segmentAgreementSourceList = '/source/list';
    this.segmentAgreementInsert = '/conservationagreement/save';
    this.segmentAlliedCategoryList = '/alliedcategory/list';
    this.segmentAlliedCategoryInsert = '/alliedcategory/save';
    this.segmentAlliedSearch = '/allied/search/';
    this.segmentCommitmentsInsert = '/commitment/save';
    this.segmentCommitmentsSearch = '/commitment/search/';
    this.segmentAlliedInsert = '/allied/save';
    this.segmentAlliedDelete = '/allied/delete/';
  }
  uploadShape(file: File) {
    const fd = new FormData();
    fd.append('file', file, file.name);
    return this.apiService.post(
      `${environment.apiUrl}/conservationagreement/upload`,
      fd
    );
  }
  getServices(url: string): Observable<any> {
    return this.apiService.get(url, null, true);
  }
  agreementSearch(item): Observable<any> {
    return this.apiService.postFormData(
      `${environment.apiUrl}${this.segmentSearch}`,
      item
    );
  }
  agreementDetail(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentDetail}/${id}`
    );
  }
  agreementStateList(): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentAgreementStateList}`
    );
  }
  agreementSourceList(): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentAgreementSourceList}`
    );
  }
  departmentList(): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}/district/listdepartment`
    );
  }
  searchProvinces(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}/district/searchbydepartment/${id}`
    );
  }
  searchDistricts(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}/district/searchbyprovince/${id}`
    );
  }
  agreementInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentAgreementInsert}`,
      item,
      null
    );
  }
  alliedCategoryList(): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentAlliedCategoryList}`
    );
  }
  alliedCategoryInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentAlliedInsert}`,
      item,
      null
    );
  }
  alliedSearch(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentAlliedSearch}${id}`
    );
  }
  commitmentsInsert(item): Observable<any> {
    return this.apiService.post(
      `${environment.apiUrl}${this.segmentCommitmentsInsert}`,
      item,
      null
    );
  }
  commitmentsSearch(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentCommitmentsSearch}${id}`
    );
  }
  alliedDelete(id): Observable<any> {
    return this.apiService.get(
      `${environment.apiUrl}${this.segmentAlliedDelete}${id}`
    );
  }
}
