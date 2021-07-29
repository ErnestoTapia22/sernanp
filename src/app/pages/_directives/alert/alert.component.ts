import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  ViewEncapsulation,
  Output,
  EventEmitter,
} from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Subscription } from 'rxjs';
import { Alert, AlertSettings } from '../../../_models/alert';
import { AlertService } from '../../../_services/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
  encapsulation: ViewEncapsulation.None,
})
export class AlertComponent implements OnInit, OnDestroy {
  @Input() id = 'default-alert';
  @Input() fade = true;
  @Output() hasAlerts = new EventEmitter<number>();

  alerts: Alert[] = [];
  alertSubscription: Subscription;
  routerSubscription: Subscription;

  constructor(private router: Router, private alertService: AlertService) {}

  ngOnInit(): void {
    this.alertSubscription = this.alertService
      .onAlert(this.id)
      .subscribe((alert) => {
        if (!alert.message) {
          this.alerts = [];
          return;
        }
        this.alerts.push(alert);
        if (alert.autoClose) {
          setTimeout(() => {
            this.removeAlert(alert);
          }, 3000);
        }
        this.hasAlerts.emit(this.alerts.length);
      });
    this.routerSubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        this.alertService.clear(this.id);
      }
    });
  }
  ngOnDestroy() {
    this.alertSubscription.unsubscribe();
    this.routerSubscription.unsubscribe();
  }
  removeAlert(alert: Alert) {
    if (!this.alerts.includes(alert)) return;
    if (this.fade) {
      this.alerts.find((x) => x == alert).fade = true;
      setTimeout(() => {
        this.alerts = this.alerts.filter((x) => x !== alert);
      }, 2500);
    } else {
      this.alerts = this.alerts.filter((x) => x !== alert);
    }
  }
  cssClass(alert: Alert) {
    if (!alert) return;
    const classes = [];
    const alertTypeClass = {
      [AlertSettings.SUCCESS]: 'success',
      [AlertSettings.ERROR]: 'danger',
      [AlertSettings.INFO]: 'info',
      [AlertSettings.WARNING]: 'warning',
    };
    classes.push(alertTypeClass[alert.alertType]);
    if (alert.fade) {
      classes.push('fade');
    }
    return classes.join(' ');
  }
}
