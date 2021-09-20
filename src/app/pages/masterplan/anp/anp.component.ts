import { Component, OnDestroy, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnpService } from '../../../_services/masterplan/anp/anp.service';
import { AlertService } from '@app/_services/base/alert.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, Observable, of } from 'rxjs';

@Component({
  selector: 'app-anp',
  templateUrl: './anp.component.html',
  styleUrls: ['./anp.component.css'],
})
export class AnpComponent implements OnInit, OnDestroy {
  inboundClick = true;
  pageSize: any[] = [];
  selectedPageSize: number = 10;
  page: Number = 0;
  total: Number = 0;
  items: any[];
  form: FormGroup;
  isLoading: Boolean = false;
  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });

  constructor(
    private anpService: AnpService,
    private fb: FormBuilder,
    private alertsService: AlertService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    this.pageSize = [
      { name: '5/PÁGINA', value: 5 },
      { name: '10/PÁGINA', value: 10 },
      { name: '20/PÁGINA', value: 20 },
      { name: '30/PÁGINA', value: 30 },
    ];

    this.items = [];
    this.initQuery();
    this.onSearch();

    // this.listAnp();
  }
  getPage(page: number) {
    this.parseData('paginator', 'offset', page);
    this.onSearch();
  }
  onChangePageSize(e) {
    // console.log();
    this.parseData('paginator', 'limit', this.selectedPageSize);
    this.parseData('paginator', 'offset', 1);
    this.onSearch();
  }
  onSearch() {
    try {
      this.spinner.show();
      if (this.form.invalid) {
        this.spinner.hide();
        return;
      }
      this.anpService
        .anpSearch(this.queryObserver.getValue())
        .subscribe((response) => {
          if (
            response &&
            response.items &&
            response.items !== undefined &&
            response.items !== null
          ) {
            this.items = response.items;
            this.total = response.paginator.total;
            this.page = response.paginator.offset;
            // this.pageSize = response.paginator.limit;
            this.spinner.hide();
          }
          this.spinner.hide();
        });
    } catch (error) {
      this.spinner.hide();
      this.alertsService.error('error :' + error, 'Error');
    }
  }
  search(filters: any) {
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters);
    this.queryObserver.next(q);
    this.onSearch();
  }
  buildForm() {
    this.form = this.fb.group({
      code: ['', Validators.compose([Validators.maxLength(10)])],
      name: [''],
    });
  }
  get f() {
    return this.form.controls;
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
  cleanForm() {
    this.form.reset();
    this.initQuery();
    this.onSearch();
  }
  initQuery() {
    let paginator = {
      limit: 10,
      offset: '1',
      sort: 'name',
      order: 'asc',
    };
    let item = {
      name: '',
      code: '',
    };
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
  }
}
