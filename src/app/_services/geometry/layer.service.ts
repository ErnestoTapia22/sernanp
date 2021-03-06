import { Injectable } from '@angular/core';
import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
import { lastValueFrom, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { AlertService } from '../base/alert.service';
import { ApiBaseService } from '../base/api-base.service';

@Injectable({
  providedIn: 'root',
})
export class LayerService {
  private jsonLayer: string;
  private legendLayer: string;
  private urlInitialLayerJson: string;

  constructor(
    private apiService: ApiBaseService,
    private alertService: AlertService
  ) {
    this.jsonLayer = '?f=json';
    this.legendLayer = '/legend?f=pjson';
    this.urlInitialLayerJson = `${environment.initialLayers}${this.jsonLayer}`;
  }

  getInitialLayersJson(url): Observable<any> {
    return this.apiService.get(`${url}${this.jsonLayer}`);
  }
  getLegendLayersJson(url): Observable<any> {
    return this.apiService.get(`${url}${this.legendLayer}`);
  }
  getInitialLayers(): Observable<any> {
    return this.apiService.get(`${environment.initialLayers}`);
  }

  async getFormatLayersJson2(layer): Promise<any> {
    try {
      const layers$ = this.getInitialLayersJson(layer.url);
      const layers$$ = await lastValueFrom(layers$);
      // console.log(layers$$);
      const legendLagers$ = this.getLegendLayersJson(layer.url);
      const legendLayers$$ = await lastValueFrom(legendLagers$);
      if (layers$$ && layers$$.layers && layers$$.layers.length > 0) {
        const json = [];
        let item = layers$$;
        item.layers.forEach((t) => {
          t.parentLayer = { id: t.parentLayerId };
          t.defaultVisibility =
            layer.visible === undefined || layer.visible === null
              ? t.defaultVisibility
              : layer.visible;
        });
        item.layers.forEach((t) => {
          t.sublayers = item.layers.filter((t3) => t3.parentLayer.id === t.id);
        });
        item.allSublayers = item.layers;
        item.sublayers = item.allSublayers.filter(
          (t) => t.parentLayer.id === -1
        );

        item.sublayers.forEach((t) => {
          this._filterParents(
            t,
            json,
            layer.uuid,
            legendLayers$$ ? legendLayers$$ : [],
            layer.visible
          );
        });

        item.json = json;
        layer.layers = item.allSublayers;
        layer.json = item.json;
      }
    } catch (error) {
      this.alertService.error(error, 'Error al traer el json');
    }
    return layer;
  }

  _filterParents(item, json, parentId, legendLayers, visible) {
    let item2 = {
      id: item.id,
      title: item.name,
      visible:
        visible === undefined || visible === null
          ? item.defaultVisibility
          : visible,
      text: item.name,
      value: `${parentId}_${item.id}`,
      checked:
        visible === undefined || visible === null
          ? item.defaultVisibility
          : visible,
      collapsed: false,
      disabled: false,
      parentId: parentId,
      legends:
        (legendLayers.layers.find((t2) => t2.layerId === item.id) || {})
          .legend || [],
      children: [],
    };
    json.push(item2);
    if (item.sublayers.length > 0) {
      item.sublayers.forEach((t) => {
        this._filterParents(t, item2.children, parentId, legendLayers, visible);
      });
    }
  }
  async TestWFSLayer(): Promise<any> {
    const featureLayer = new FeatureLayer({
      url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/GasNatural/Produccion/FeatureServer',
      outFields: ['*'],
      popupEnabled: false,
      id: 'featureTest',
    });
  }
}
