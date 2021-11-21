import { Injectable } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Observable, BehaviorSubject } from 'rxjs';
import { filter } from 'rxjs/operators';
import { Alert, AlertSettings } from '../../_models/base/alert';

import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private subject = new BehaviorSubject<Alert>(null);
  private keepAfterNavigationChange = false;
  private defaultId = 'default-alert';
  private defaultTittle = 'Notificación';

  constructor(private router: Router) {
    // // clear alert message on route change
    // router.events.subscribe((event) => {
    //   if (event instanceof NavigationStart) {
    //     if (this.keepAfterNavigationChange) {
    //       // only keep for a single location change
    //       this.keepAfterNavigationChange = false;
    //     } else {
    //       // clear alert
    //       this.clear();
    //       //this.subject.next();
    //     }
    //   }
    // });
  }
  onAlert(id = this.defaultId): Observable<Alert> {
    return this.subject.asObservable().pipe(filter((x) => x && x.id === id));
  }
  success(message: string, tittle?: string, options?: any) {
    this.alert(
      new Alert({
        ...options,
        alertType: AlertSettings.SUCCESS,
        message,
        tittle,
      })
    );
  }

  error(message: string, tittle?: string, options?: any) {
    this.alert(
      new Alert({ ...options, alertType: AlertSettings.ERROR, message, tittle })
    );
  }

  info(message: string, tittle?: string, options?: any) {
    this.alert(
      new Alert({ ...options, alertType: AlertSettings.INFO, message, tittle })
    );
  }

  warn(message: string, tittle?: string, options?: any) {
    this.alert(
      new Alert({
        ...options,
        alertType: AlertSettings.WARNING,
        message,
        tittle,
      })
    );
  }

  alert(alert: Alert) {
    alert.id = alert.id || this.defaultId;
    alert.tittle = alert.tittle || this.defaultTittle;
    this.subject.next(alert);
  }

  clear(id = this.defaultId) {
    // clear alerts
    this.subject.next(new Alert({ id }));
  }
}
