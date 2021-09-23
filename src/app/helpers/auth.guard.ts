import { Injectable } from '@angular/core';
import {
  Router,
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
} from '@angular/router';
import { isNil } from 'lodash';
import { environment } from '../../environments/environment';
import { AuthenticationService } from '@app/_services/auth/authentication.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authenticationService: AuthenticationService
  ) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const user = this.authenticationService.userValue;
    if (!isNil(user)) {
      // if (route.data.roles && route.data.roles.indexOf(user.role.name) === -1) {
      //   this.router.navigate(['/authentication/no-user']);
      //   return false;
      // }
      if (
        route.data.module &&
        user.modules
          .map((value) => {
            return value.path;
          })
          .indexOf(route.data.module) === -1
      ) {
        this.router.navigate(['/authentication/no-user']);
        return false;
      }
      return true;
    }
    window.location.href = environment.logOutUrl;
    // this.router.navigate(['/map'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
