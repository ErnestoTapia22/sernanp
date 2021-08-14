import { Component, OnInit } from '@angular/core';
import { MonitoringService } from '../../../../_services/base/monitoring.service';
import { AlertService } from '../../../../_services/base/alert.service';
import { NgxSpinnerService } from 'ngx-spinner';

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
  constructor(
    private monitoringService: MonitoringService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.total = 10;
    this.pageSize = 5;
    // this.spinner.show();
    // setTimeout(() => {
    //   this.spinner.hide();
    // }, 5000);
    //this.spinner.show();
    this.getItems();
  }
  onDateSelect(e) {
    console.log(e);
  }
  async getItems() {
    try {
      this.isLoading = true;
      this.spinner.show();
      const items = await this.monitoringService.agreementMonitoringList();
      console.log(items);
      if (items && items.length > 0) {
        this.agreementList = items;
        this.isLoading = false;
        this.spinner.hide();
      } else {
        this.isLoading = false;
        this.spinner.hide();
      }
    } catch (error) {
      this.isLoading = false;
      this.spinner.hide();
      this.alertService.error('Error al traer acuerdos', 'Error');
    }
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
    console.log(event);
  }
}
