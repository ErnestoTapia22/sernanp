import { InitService } from '../_services/init-service/init.service';
import { lastValueFrom } from 'rxjs';
import { AlertService } from '../_services/base/alert.service';

export function initAppFactory(
  initService: InitService,
  alertService: AlertService
) {
  return async () => {
    try {
      // const result = await lastValueFrom(initService.getAuthorization());
      // localStorage.setItem('auth', JSON.stringify(result));
    } catch (err) {
      localStorage.setItem('auth', JSON.stringify({}));
      alertService.error('Error en la autenticación: ' + err, 'Error');
    }
  };
}
