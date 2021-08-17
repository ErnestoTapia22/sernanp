import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'app-monitoring',
  templateUrl: './monitoring.component.html',
  styleUrls: ['./monitoring.component.css'],
})
export class MonitoringComponent implements OnInit {
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
    this.total = 10;
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
      offset: '0',
      sort: 'name',
      order: 'asc',
    };
    let item = {
      code: '',
    };
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
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
          console.log(data);

          if (data && data.length > 0) {
            this.agreementList = data;
            this.isLoading = false;
            this.spinner.hide();
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
    // this.loading = true;
    // this.asyncMeals = serverCall(this.meals, page).pipe(
    //     tap(res => {
    //         this.total = res.total;
    //         this.p = page;
    //         this.loading = false;
    //     }),
    //     map(res => res.items)
    // );
  }
  onChangePageSize(event) {
    console.log(this.f.pageSizes.value);
    // const q = this.queryObserver.getValue();
    // q.paginator['limit'] = this.f.pageSizes.value;
    this.parseData('paginator', 'limit', parseInt(this.f.pageSizes.value));

    this.onSearch();
    // this.queryObserver.next({item:this.f.})
  }
  search(filters: any): void {
    console.log(filters);
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters);
    this.queryObserver.next(q);
    console.log(this.queryObserver.getValue());

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
      pageSizes: ['', Validators.compose([])],
    });
  }
}
