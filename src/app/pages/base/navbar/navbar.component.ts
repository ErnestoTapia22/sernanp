import { Component, OnInit } from '@angular/core';
import { Event, NavigationEnd, Router } from '@angular/router';
import { User } from '@app/_models/auth/user';
import { AuthenticationService } from '@app/_services/auth/authentication.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AlertService } from '../../../_services/base/alert.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  public sidebarOpened = false;
  dataUser: any;
  hasAlerts: number = 0;
  user: User;
  isLogedIn: boolean = false;
  modalRef: NgbModalRef;
  notificationsList: any[] = [];
  constructor(
    private alertService: AlertService,
    private authenticationService: AuthenticationService,
    private modalService: NgbModal,

    private router: Router
  ) {
    this.router.events.subscribe((event: Event) => {
      if (event instanceof NavigationEnd) {
        this.callNotification();
      }
    });
  }

  ngOnInit(): void {
    this.clickNav(null);

    this.user = this.authenticationService.userValue;
    if (this.user) {
      this.isLogedIn = true;
    }
    this.callNotification();
  }

  toggleOffcanvas() {
    this.sidebarOpened = !this.sidebarOpened;
    if (this.sidebarOpened) {
      document.querySelector('.sidebar-offcanvas').classList.add('active');
    } else {
      document.querySelector('.sidebar-offcanvas').classList.remove('active');
    }
  }
  hasAlertsForDisplay(e) {
    this.hasAlerts = e;
  }
  clickNav(evt) {
    const elmBody = document.querySelector('body');
    const elmNavTogglerIcon = document.querySelector('.nav-toggler i');
    elmBody.addEventListener('resize', this.setResize);
    if (window.innerWidth < 768) {
      elmBody.classList.toggle('show-sidebar');
    }

    if (elmNavTogglerIcon) {
      if (elmBody.classList.contains('show-sidebar')) {
        elmNavTogglerIcon.classList.remove('bi-list');
        elmNavTogglerIcon.classList.add('bi-x-lg');
      } else {
        elmNavTogglerIcon.classList.remove('bi-x-lg');
        elmNavTogglerIcon.classList.add('bi-list');
      }
    }
  }
  setResize = () => {
    let width = window.innerWidth > 0 ? window.innerWidth : screen.width;
    let topOffset = 55;

    const elmPageBody = document.querySelector('body');
    const elmNavbarBrand = document.querySelector('.navbar-brand span');
    const elmSidebartogglerIcon = document.querySelector('.sidebartoggler i');
    const elmPageWrapper = document.querySelector('.page-wrapper');

    elmPageBody.classList.add('mini-sidebar');

    var height =
      (window.innerHeight > 0 ? window.innerHeight : screen.height) - 1;
    height = height - topOffset;
    if (height < 1) height = 1;
    if (height > topOffset) {
      if (elmPageWrapper)
        elmPageWrapper.setAttribute('style', 'min-height:' + height + 'px'); //style.minHeight = (height) + "px";
    }
  };
  logout() {
    this.authenticationService.logout();
    this.isLogedIn = false;
  }
  onViewNotifications(content) {
    this.modalRef = this.modalService.open(content, {
      size: 'md',
      centered: true,
    });
  }
  onViewNotification(id) {
    this.modalRef.close();
    // this.router.navigate(['/notifications/' + id]);
  }
  callNotification() {
    //const sampleData = [
    //  {
    //    id: 1,
    //    title: 'Acuerdo 20211009',
    //    message: 'El acuerdo esta a punto de vencer',
    //    time: '3 days ago',
    //    action: '',
    //  },
    //  {
    //    id: 2,
    //    title: 'Acuerdo 20211006',
    //    message: 'El acuerdo esta a punto de vencer',
    //    time: '3 days ago',
    //    action: '',
    //  },
    //  {
    //    id: 3,
    //    title: 'Acuerdo 20211005',
    //    message: 'El acuerdo esta a punto de vencer',
    //    time: '3 days ago',
    //    action: '',
    //  },
    //];
    //this.notificationsList = sampleData;

    try {
      this.authenticationService.getNotifications().subscribe((response) => {
        if (response && response.items.length > 0) {
          this.notificationsList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer las notificaciones', 'Error', {
        autoClose: true,
      });
    }
  }
}
