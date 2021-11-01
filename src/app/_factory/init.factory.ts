import { InitService } from '../_services/init-service/init.service';
import { lastValueFrom } from 'rxjs';
import { AlertService } from '../_services/base/alert.service';

export function initAppFactory(
  initService: InitService,
  alertService: AlertService
) {
  return async () => {
    try {
      // console.log('initAppFactory');
      // const user = localStorage.getItem('user');
      // console.log(JSON.parse(user));
      // if (user !== null && user !== undefined) {
      //   console.log(JSON.parse(user).token);
      //   if (tokenExpired(JSON.parse(user).token.substring(0, 8))) {
      //     localStorage.removeItem('user');
      //   } else {
      //     // token valid
      //   }
      // }
      // const result = await lastValueFrom(initService.getAuthorization());
      // localStorage.setItem('auth', JSON.stringify(result));
    } catch (err) {
      localStorage.setItem('auth', JSON.stringify({}));
      alertService.error('Error en la autenticación: ' + err, 'Error');
    }
  };
  function tokenExpired(token: string) {
    const expiry = JSON.parse(atob(token.split('.')[1])).exp;
    return Math.floor(new Date().getTime() / 1000) >= expiry;
  }
}
