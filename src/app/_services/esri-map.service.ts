import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EsriMapService {
  panRequest = new Subject<void>();
  panComplete = new Subject<void>();
  wonderCoordinates;

  panToWonder(wonderCoordinates) {
    this.wonderCoordinates = wonderCoordinates;
    this.panRequest.next();
  }

  panToWonderComplete() {
    this.panComplete.next();
  }

  constructor() {}
}
