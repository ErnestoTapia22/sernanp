import { Directive, Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '../../_services/alert.service';
import { Alert, AlertType } from '../../_models/alert';
@Directive({
  selector: 'app-alert',
})
export class AlertDirective implements OnInit {
  private subscription: Subscription;
  message: any;
  alerts: Alert[] = [];
  constructor(private alertService: AlertService) {
    this.alertService.getMessage().subscribe((alert: Alert) => {
      if (!alert) {
        // clear alerts when an empty alert is received
        this.alerts = [];
        return;
      }

      // add alert to array
      this.alerts.push(alert);
    });
  }
  ngOnInit() {
    /* this.subscription = this.alertService.getMessage().subscribe(message => {

         this.message = message;
     });*/
    this.alertService.getMessage().subscribe((alert: Alert) => {
      if (!alert) {
        // clear alerts when an empty alert is received
        this.alerts = [];
        return;
      }

      // add alert to array
      this.alerts.push(alert);
    });
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter((x) => x !== alert);
  }
  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }

    // return css class based on alert type
    switch (alert.type) {
      case AlertType.Success:
        return 'alert alert-success';
      case AlertType.Error:
        return 'alert alert-danger';
      case AlertType.Info:
        return 'alert alert-info';
      case AlertType.Warning:
        return 'alert alert-warning';
    }
  }
}
