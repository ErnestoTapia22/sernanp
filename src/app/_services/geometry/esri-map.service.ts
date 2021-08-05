import { Injectable, EventEmitter } from '@angular/core';
import { Subject } from 'rxjs';
import MapView from '@arcgis/core/views/MapView';
import WebMap from '@arcgis/core/WebMap';
import Widget from '@arcgis/core/widgets/Widget';
import Layer from '@arcgis/core/layers/Layer';
import Map from '@arcgis/core/Map';
import layer from '@arcgis/core/layers/MapImageLayer';
import Popup from '@arcgis/core/widgets/Popup';

export type Position =
  | 'bottom-leading'
  | 'bottom-left'
  | 'bottom-right'
  | 'bottom-trailing'
  | 'top-left'
  | 'top-leading'
  | 'top-right'
  | 'top-trailing'
  | 'manual';

@Injectable({
  providedIn: 'root',
})
export class EsriMapService {
  webMap!: WebMap;
  view!: MapView;
  map!: Map;
  loaded = false;
  defaultPopup: any;
  isLoaded = new EventEmitter();

  constructor() {}

  loadMap(props: {
    basemap: any;
    container: any;
    center: any;
    zoom: any;
  }): void {
    this.map = new Map({ basemap: props.basemap });
    this.defaultPopup = new Popup({ defaultPopupTemplateEnabled: true });
    // this.defaultPopup.container = document.getElementById('popupContainerDiv');

    this.defaultPopup.defaultPopupTemplateEnabled = true;

    this.defaultPopup.dockOptions = {
      buttonEnabled: false,
    };
    this.view = new MapView({
      container: props.container,
      map: this.map,
      center: props.center,
      zoom: props.zoom,
      popup: this.defaultPopup,
    });
    this.loaded = true;
    this.isLoaded.emit({
      map: this.map,
      view: this.view,
    });
  }

  addLayer(layer: Layer, clearLayers?: boolean): void {
    if (clearLayers) {
      this.view.map.removeAll();
    }
    this.view.map.add(layer);
  }
  addWidget(
    component: string | HTMLElement | Widget | any[],
    position: Position
  ): void {
    this.view.ui.add(component, position);
  }
}
