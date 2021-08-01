import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { environment } from '../../environments/environment';
import { ApiBaseService } from './api-base.service';
import { AlertService } from '../_services/alert.service';
import { lastValueFrom } from 'rxjs';
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

  getInitialLayersJson(url): Observable<any> {
    return this.apiService.get(`${url}${this.jsonLayer}`);
  }
  getInitialLayers(): Observable<any> {
    return this.apiService.get(`${environment.initialLayers}`);
  }

  recursiveConstructor(responseLayers: any, formatedLayers?: any, copyResponseLayers?: any) {
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

  async getFormatLayersJson2(layer): Promise<any> {
    try {
      const layers$ = this.getInitialLayersJson(layer.url);
      const layers$$ = await lastValueFrom(layers$);
      console.log(layers$$);
      if (layers$$ && layers$$.layers && layers$$.layers.length > 0) {
        const responseLayers = layers$$.layers;
        const json = [];
        let item = layers$$;
        item.layers.forEach(t => t.parentLayer = { id: t.parentLayerId });        
        item.layers.forEach(t => {          
          t.sublayers = item.layers.filter(t3 => t3.parentLayer.id === t.id);
        });
        item.allSublayers = item.layers;
        item.sublayers = item.allSublayers.filter(t => t.parentLayer.id === -1);
        //console.log(item.sublayers);
        item.sublayers.forEach(t=> {
          this._filterParents(t, json);
        });
        //this._filterParents(item.sublayers, json);
        item.json = json;
        console.log(json);
        const jsonSublayers = [
          {
            id: 0,
            title: 'Geología - Franja Sur',
            visible: true,
            text: 'Geología - Franja Sur',
            value: 0,
            checked: true,
            collapsed: true,
            disabled: false,
            children: [
              {
                id: 8,
                title: 'Geología 100K',
                visible: true,
                text: 'Geología 100K',
                value: 8,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 10,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 10,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 9,
                    title: 'Contacto',
                    visible: true,
                    text: 'Geología 100K',
                    value: 9,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 10,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 10,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 9,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 9,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
              {
                id: 5,
                title: 'Geología 50K',
                visible: true,
                text: 'Geología 50K',
                value: 5,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 7,
                    title: 'Geología 50K Franjas',
                    visible: true,
                    text: 'Geología 50K Franjas',
                    value: 7,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 6,
                    title: 'Contacto 50K Franjas',
                    visible: true,
                    text: 'Contacto 50K Franjas',
                    value: 6,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 7,
                    title: 'Geología 50K Franjas',
                    visible: true,
                    text: 'Geología 50K Franjas',
                    value: 7,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 6,
                    title: 'Contacto 50K Franjas',
                    visible: true,
                    text: 'Contacto 50K Franjas',
                    value: 6,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
              {
                id: 1,
                title: 'Hojas',
                visible: true,
                text: 'Hojas',
                value: 1,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 4,
                    title: 'Boletines',
                    visible: true,
                    text: 'Boletines',
                    value: 4,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 3,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 3,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 2,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 2,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 4,
                    title: 'Boletines',
                    visible: true,
                    text: 'Boletines',
                    value: 4,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 3,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 3,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 2,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 2,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
            ],
            sublayers: [
              {
                id: 8,
                title: 'Geología 100K',
                visible: true,
                text: 'Geología 100K',
                value: 8,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 10,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 10,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 9,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 9,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 10,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 10,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 9,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 9,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
              {
                id: 5,
                title: 'Geología 50K',
                visible: true,
                text: 'Geología 50K',
                value: 5,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 7,
                    title: 'Geología 50K Franjas',
                    visible: true,
                    text: 'Geología 50K Franjas',
                    value: 7,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 6,
                    title: 'Contacto 50K Franjas',
                    visible: true,
                    text: 'Contacto 50K Franjas',
                    value: 6,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 7,
                    title: 'Geología 50K Franjas',
                    visible: true,
                    text: 'Geología 50K Franjas',
                    value: 7,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 6,
                    title: 'Contacto 50K Franjas',
                    visible: true,
                    text: 'Contacto 50K Franjas',
                    value: 6,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
              {
                id: 1,
                title: 'Hojas',
                visible: true,
                text: 'Hojas',
                value: 1,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 4,
                    title: 'Boletines',
                    visible: true,
                    text: 'Boletines',
                    value: 4,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 3,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 3,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 2,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 2,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 4,
                    title: 'Boletines',
                    visible: true,
                    text: 'Boletines',
                    value: 4,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    sublayers: [],
                  },
                  {
                    id: 3,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 3,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 2,
                    title: 'Franjas',
                    visible: true,
                    text: 'Franjas',
                    value: 2,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
            ],
          },
          {
            id: 11,
            title: 'Geología - Boletin Serie A',
            visible: true,
            text: 'Geología - Boletin Serie A',
            value: 11,
            checked: true,
            collapsed: true,
            disabled: false,
            children: [
              {
                id: 12,
                title: 'Geología 100K',
                visible: true,
                text: 'Geología 100K',
                value: 12,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 14,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 14,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 13,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 13,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 14,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 14,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 13,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 13,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
            ],
            sublayers: [
              {
                id: 12,
                title: 'Geología 100K',
                visible: true,
                text: 'Geología 100K',
                value: 12,
                checked: true,
                collapsed: true,
                disabled: false,
                children: [
                  {
                    id: 14,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 14,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 13,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 13,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
                sublayers: [
                  {
                    id: 14,
                    title: 'Geología 100K',
                    visible: true,
                    text: 'Geología 100K',
                    value: 14,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                  {
                    id: 13,
                    title: 'Contacto',
                    visible: true,
                    text: 'Contacto',
                    value: 13,
                    checked: true,
                    collapsed: true,
                    disabled: false,
                    children: [],
                    sublayers: [],
                  },
                ],
              },
            ],
          },
          {
            id: 15,
            title: 'Relieve Topográfico',
            visible: true,
            text: 'Relieve Topográfico',
            value: 15,
            checked: true,
            collapsed: true,
            disabled: false,
            children: [],
            sublayers: [],
          },
        ];
        console.log(jsonSublayers);
        //return json;
        layer.layers = item.allSublayers;
        layer.json = item.json;
      }
    } catch (error) {
      this.alertService.error(error, 'Error al traer el json');
    }
    return layer;
  }

  _filterParents(item, json) {
    let item2 = {
      id: item.id,     
      title: item.name,
      visible: item.defaultVisibility,
      text: item.name,
      value: item.id,
      checked: item.defaultVisibility,
      collapsed: false,
      disabled: false,
      children:[]
    };
    json.push(item2);
    if (item.sublayers.length > 0) {
        item.sublayers.forEach(t=> {
          this._filterParents(t, item2.children);
        });
    }      
  }
}
