import { Component, OnInit, OnDestroy } from '@angular/core';
import { MonitoringService } from '../../../../_services/base/monitoring.service';
import { AgreementService } from '../../../../_services/base/agreement.service';
import { AlertService } from '../../../../_services/base/alert.service';
import { NgxSpinnerService } from 'ngx-spinner';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { query } from '@angular/animations';
import { runInThisContext } from 'vm';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  agreementList: any[];
  total: number;
  isLoading: boolean = false;
  pageSize: any;
  page: Number;
  form: FormGroup;
  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });
  parsed: any;
  agreementStateList: any[] = [];
  agreementSourceList: any[] = [];

  constructor(
    private agreementService: AgreementService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.total = 0;
    this.pageSize = 10;
    this.buildForm();
    // this.spinner.show();
    // setTimeout(() => {
    //   this.spinner.hide();
    // }, 5000);
    //this.spinner.show();
    // this.getItems();
    this.fillSelects();
    this.initQuery();
    this.onSearch();
    // this.fillSelects();
  }

  onDateSelect(e) {
    console.log(e);
  }
  onSearch() {
    try {
      this.isLoading = true;
      this.spinner.show();
      this.agreementService
        .agreementSearch(this.queryObserver.getValue())
        .subscribe((data) => {
          if (data && data.items && data.items.length > 0) {
            this.agreementList = data.items;
            this.total = data.total;
            this.page = data.paginator.offset;
            this.pageSize = data.paginator.limit;
            this.isLoading = false;
            this.spinner.hide();
            this.setTableHeight(this.pageSize, data.items.length);
          } else {
            this.isLoading = false;
            this.spinner.hide();
          }
        });
    } catch (error) {
      this.isLoading = false;
      this.spinner.hide();
      this.alertService.error('Error al traer acuerdos:' + error, 'Error');
    }
  }
  parseData(parent, key, value) {
    const item = this.queryObserver.getValue();

    if (typeof item[parent] === 'string') {
      let parsed = JSON.parse(item[parent]);
      parsed[key] = value;
      item[parent] = JSON.stringify(parsed);
      this.queryObserver.next(item);
    }
  }

  get f() {
    return this.form.controls;
  }
  getPage(page: number) {
    this.parseData('paginator', 'offset', page);
    this.onSearch();
  }
  onChangePageSize(event) {
    // const q = this.queryObserver.getValue();
    // q.paginator['limit'] = this.f.pageSizes.value;
    this.parseData('paginator', 'limit', parseInt(this.f.pageSize.value));
    this.parseData('paginator', 'offset', 1);
    this.onSearch();
    // this.queryObserver.next({item:this.f.})
  }
  search(filters: any): void {
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters);
    this.queryObserver.next(q);

    this.onSearch();
  }

  buildForm(): void {
    this.form = this.fb.group({
      code: ['', Validators.compose([Validators.maxLength(10)])],
      firm: [
        '',
        Validators.compose([
          Validators.pattern(
            '/^(((0[1-9]|[12]d|3[01])/(0[13578]|1[02])/((19|[2-9]d)d{2}))|((0[1-9]|[12]d|30)/(0[13456789]|1[012])/((19|[2-9]d)d{2}))|((0[1-9]|1d|2[0-8])/02/((19|[2-9]d)d{2}))|(29/02/((1[6-9]|[2-9]d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g'
          ),
        ]),
      ],
      category: [''],
      state: [''],
      pageSize: ['10'],
      name: [''],
      agreementState: this.fb.group({
        id: [0],
      }),
      source: this.fb.group({
        id: [0],
      }),
    });
  }
  ngOnDestroy() {
    this.queryObserver.unsubscribe();
  }
  setTableHeight(rows, itemsLength?: number) {
    if (rows !== undefined && rows !== null) {
      const cm = document.getElementById('tableBody');
      if (itemsLength < rows) {
        const height = 50.838 * itemsLength;
        cm.setAttribute('style', `height:${height}px`);
      } else {
        const height = 50.838 * parseInt(rows);
        cm.setAttribute('style', `height:${height}px`);
      }
    }
  }
  fillSelects() {
    try {
      this.agreementService.agreementStateList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.agreementStateList = response.items;
        }
      });
      this.agreementService.agreementSourceList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.agreementSourceList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error(
        'Error al traer la lista de estados del acuerdo o la lista de fuente de financiamiento',
        'error',
        { autoClose: true }
      );
    }
  }
  clearForm() {
    this.form.reset({
      agreementState: { id: 0 },
      source: { id: 0 },
    });
    this.initQuery();
    this.onSearch();
  }
  initQuery() {
    let paginator = {
      limit: this.pageSize,
      offset: '1',
      sort: 'name',
      order: 'asc',
    };
    let item = {
      name: '',
      agreementState: { id: 0 },
      source: { id: 0 },
      code: '',
    };
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
  }
}
