import { Injectable } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { Alert, AlertType } from '../_models/alert';
@Injectable({
  providedIn: 'root',
})
export class AlertService {
  private subject = new Subject<Alert>();
  private keepAfterNavigationChange = false;

  constructor(private router: Router) {
    // clear alert message on route change
    router.events.subscribe((event) => {
      if (event instanceof NavigationStart) {
        if (this.keepAfterNavigationChange) {
          // only keep for a single location change
          this.keepAfterNavigationChange = false;
        } else {
          // clear alert
          this.clear();
          //this.subject.next();
        }
      }
    });
  }
  getMessage(): Observable<any> {
    return this.subject.asObservable();
  }
  success(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Success, message, keepAfterNavigationChange);
  }

  error(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Error, message, keepAfterNavigationChange);
  }

  info(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Info, message, keepAfterNavigationChange);
  }

  warn(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Warning, message, keepAfterNavigationChange);
  }

  alert(type: AlertType, message: string, keepAfterNavigationChange = false) {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.subject.next(<Alert>{ type: type, message: message });
  }

  clear() {
    // clear alerts
    this.subject.next();
  }
}
