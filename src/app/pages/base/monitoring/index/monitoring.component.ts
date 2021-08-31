import { Component, OnInit, OnDestroy } from '@angular/core';
import { MonitoringService } from '../../../../_services/base/monitoring.service';
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
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-monitoring',
  templateUrl: './monitoring.component.html',
  styleUrls: ['./monitoring.component.css'],
})
export class MonitoringComponent implements OnInit, OnDestroy {
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
  constructor(
    private monitoringService: MonitoringService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.total = 0;
    this.pageSize = 5;
    this.builForm();
    // this.spinner.show();
    // setTimeout(() => {
    //   this.spinner.hide();
    // }, 5000);
    //this.spinner.show();
    // this.getItems();

    let paginator = {
      limit: this.pageSize,
      offset: '1',
      sort: 'name',
      order: 'asc',
    };
    let item = {
      name: '',
    };
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
    this.onSearch();
  }
  onDateSelect(e) {
    console.log(e);
  }
  onSearch() {
    try {
      this.isLoading = true;
      this.spinner.show();
      this.monitoringService
        .agreementSearch(this.queryObserver.getValue())
        .subscribe((data) => {
          if (data && data.items && data.items.length > 0) {
            this.agreementList = data.items;
            this.total = data.total;
            this.page = data.paginator.offset;
            this.pageSize = data.paginator.limit;
            this.isLoading = false;
            this.spinner.hide();
            this.setTableHeight(this.pageSize);
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
    // debugger;
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

    this.onSearch();
    // this.queryObserver.next({item:this.f.})
  }
  search(filters: any): void {
    // const q = this.queryObserver.getValue();
    // q.item = JSON.stringify(filters);
    // this.queryObserver.next(q);
    // console.log(this.queryObserver.getValue());

    this.onSearch();
  }

  builForm(): void {
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
      category: ['', Validators.compose([])],
      state: ['', Validators.compose([])],
      pageSize: ['5', Validators.compose([])],
    });
  }
  ngOnDestroy() {
    this.queryObserver.unsubscribe();
  }
  setTableHeight(rows) {
    if (rows !== undefined && rows !== null) {
      const cm = document.getElementById('tableBody');
      const height = 50.838 * parseInt(rows);
      cm.setAttribute('style', `height:${height}px`);
    }
  }
}
