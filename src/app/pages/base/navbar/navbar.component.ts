import { Component, OnInit } from '@angular/core';
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
  constructor(private alertService: AlertService) {}

  ngOnInit(): void {
    this.clickNav(null);
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
      // elmNavTogglerIcon.classList.toggle('');
      // elmNavTogglerIcon.classList.toggle('bi-x-lg');
    }
  }
  setResize = () => {
    let width = window.innerWidth > 0 ? window.innerWidth : screen.width;
    let topOffset = 55;

    const elmPageBody = document.querySelector('body');
    const elmNavbarBrand = document.querySelector('.navbar-brand span');
    const elmSidebartogglerIcon = document.querySelector('.sidebartoggler i');
    const elmPageWrapper = document.querySelector('.page-wrapper');

    // console.log(elmNavbarBrand, elmSidebartogglerIcon);
    elmPageBody.classList.add('mini-sidebar');

    // if (width < 1170) {
    //     elmPageBody.classList.add('mini-sidebar');
    //     if(elmNavbarBrand) elmNavbarBrand.style.display = 'none';
    //     if(elmSidebartogglerIcon) elmSidebartogglerIcon.classList.add('fa-bars')
    // }
    // else {
    //     elmPageBody.classList.remove('mini-sidebar');
    //     if(elmNavbarBrand) elmNavbarBrand.style.display = 'block';
    // }

    var height =
      (window.innerHeight > 0 ? window.innerHeight : screen.height) - 1;
    height = height - topOffset;
    if (height < 1) height = 1;
    if (height > topOffset) {
      if (elmPageWrapper)
        elmPageWrapper.setAttribute('style', 'min-height:' + height + 'px'); //style.minHeight = (height) + "px";
    }
  };
}
