import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthenticationService } from '@app/_services/auth/authentication.service';
import { User } from '@app/_models/auth/user';
@Component({
  selector: 'app-full-layout',
  templateUrl: './full-layout.component.html',
  styleUrls: ['./full-layout.component.css'],
})
export class FullLayoutComponent implements OnInit, OnDestroy {
  items: any;

  isLogedIn: boolean = false;
  user: User;
  arrayModules: any[] = [];
  private view: any = null;
  constructor(private authenticationService: AuthenticationService) {}
  status: boolean = false;
  clickEvent() {
    this.status = !this.status;
  }
  ngOnInit(): void {
    this.user = this.authenticationService.userValue;
    if (this.user) {
      this.isLogedIn = true;
      this.arrayModules = this.user.modules.map((value) => {
        return value.path;
      });
    }
  }

  onSelectedChange(event) {
    // console.log(event);
  }

  loadMap(): void {}
  ngOnDestroy() {}
  clickAside(evt) {
    // const sidebarnav = document.getElementById('sidebarnav');
    // if (sidebarnav) {
    //   let elmA = (evt.target as Element).closest('a');
    //   if (!elmA) return;
    //   let addressValue = elmA.getAttribute('href');
    //   if (
    //     '#' == addressValue ||
    //     '' == addressValue ||
    //     '/#' == addressValue ||
    //     '#/' == addressValue ||
    //     'javascript:void(0)' == addressValue
    //   )
    //     evt.preventDefault();
    //   if (!elmA.classList.contains('active')) {
    //     let elmUls = Array.from(sidebarnav.querySelectorAll('ul'));
    //     let elmAs = Array.from(sidebarnav.querySelectorAll('a'));
    //     let prevElem = null;
    //     let nextElem = elmA.nextElementSibling;
    //     if (!nextElem) nextElem = elmA.closest('ul');
    //     if (nextElem) prevElem = nextElem.previousElementSibling;
    //     elmUls.forEach((itemUl) => {
    //       itemUl.classList.remove('in');
    //     });
    //     elmAs.forEach((itemA) => {
    //       itemA.classList.remove('active');
    //     });
    //     if (nextElem) nextElem.classList.add('in');
    //     if (prevElem) prevElem.classList.add('active');
    //     elmA.classList.add('active');
    //   } else if (elmA.classList.contains('active')) {
    //     let nextElem = elmA.nextElementSibling;
    //     if (!nextElem) nextElem = elmA.closest('ul');
    //     if (nextElem) nextElem.classList.remove('in');
    //     elmA.classList.remove('active');
    //   }
    // }
  }
}
