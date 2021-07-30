import { Component, OnInit } from '@angular/core';
import { AlertService } from '../../_services/alert.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  public sidebarOpened = false;
  dataUser: any;
  hasAlerts: number = 0;
  constructor(private alertService: AlertService) {}

  ngOnInit(): void {}

  toggleOffcanvas() {
    this.sidebarOpened = !this.sidebarOpened;
    if (this.sidebarOpened) {
      document.querySelector('.sidebar-offcanvas').classList.add('active');
    } else {
      document.querySelector('.sidebar-offcanvas').classList.remove('active');
    }
  }
  hasAlertsForDisplay(e) {
    console.log(e);
    this.hasAlerts = e;
  }
}
