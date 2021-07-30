import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiBaseService } from './api-base.service';
import { AlertService } from '../_services/alert.service';
@Injectable({
  providedIn: 'root',
})
export class LayerService {
  private jsonLayer: string;
  private urlInitialLayer: string;
  private urlInitialLayerJson: string;

  constructor(
    private apiService: ApiBaseService,
    private alertService: AlertService
  ) {
    this.jsonLayer = '?f=json';
    this.urlInitialLayerJson = `${environment.initialLayers}${this.jsonLayer}`;
  }

  getInitialLayersJson(): Observable<any> {
    return this.apiService.get(this.urlInitialLayerJson);
  }
  getInitialLayers(): Observable<any> {
    return this.apiService.get(`${environment.initialLayers}`);
  }

  recursiveConstructor(
    responseLayers: any,
    formatedLayers?: any,
    copyResponseLayers?: any
  ) {
    for (let i = 0; i < responseLayers.length; i++) {
      if (responseLayers[i]['alreadyFound'] == false) {
        let formatTemplate = {};
        formatTemplate = responseLayers[i];
        formatTemplate['text'] = responseLayers[i].name;
        formatTemplate['value'] = responseLayers[i].id;
        formatTemplate['collapsed'] = true;
        formatTemplate['children'] = [];
        if (responseLayers[i].subLayerIds !== null) {
          for (let j = 0; j < responseLayers[i].subLayerIds.length; j++) {
            let found = copyResponseLayers.find(
              (x) => x.id == copyResponseLayers[i].subLayerIds[j]
            );
            if (found) {
              found['alreadyFound'] = false;
              responseLayers[found['id']].alreadyFound = true;
              copyResponseLayers[found['id']].alreadyFound = true;
              formatTemplate['children'].push(found);
            }
          }
          if (formatTemplate['children'].length > 0) {
            this.recursiveConstructor(
              formatTemplate['children'],
              formatedLayers,
              copyResponseLayers
            );
          }
        }
        formatedLayers.push(formatTemplate);
      }
    }
    console.log(formatedLayers);
  }

  destructuring(obj, target) {
    let outPut = obj.reduce((out, { id, name }) => {});
  }

  getFormatLayersJson(): any {
    try {
      this.getInitialLayersJson().subscribe(
        (response) => {
          if (response && response.layers && response.layers.length > 0) {
            const addFound = [];
            const responseLayers = response.layers;
            console.log(responseLayers);
            for (let i = 0; i < responseLayers.length; i++) {
              let formatTemplate = {};
              formatTemplate = responseLayers[i];
              formatTemplate['alreadyFound'] = false;
              addFound.push(formatTemplate);
            }
            const formatedLayers = [];
            const object1 = [];

            const copyResponse1 = Object.assign(object1, addFound);
            const object2 = [];
            const copyResponse2 = Object.assign(object2, addFound);
            this.recursiveConstructor(
              copyResponse1,
              formatedLayers,
              copyResponse2
            );

            return formatedLayers;
          } else {
            return [];
          }
        },
        (error) => {
          this.alertService.error(
            error.errors.message,
            'Error en capas iniciales'
          );
          return [];
        }
      );
    } catch (error) {
      this.alertService.error(error.errors.message, 'Error en capas iniciales');
      return [];
    }
  }
}
