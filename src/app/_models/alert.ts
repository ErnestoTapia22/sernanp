export class Alert {
  id: string;
  alertType: string;
  tittle: string;
  message: string;
  autoClose: boolean;
  fade: boolean;
  constructor(init?: Partial<Alert>) {
    Object.assign(this, init);
  }
}
export class AlertSettings {
  public static SUCCESS = 'success';
  public static ERROR = 'error';
  public static INFO = 'info';
  public static WARNING = 'warning';
}
