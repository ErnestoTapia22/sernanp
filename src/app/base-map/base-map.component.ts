import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import MapView from '@arcgis/core/views/SceneView';
import Map from '@arcgis/core/Map';
import Graphic from '@arcgis/core/Graphic';
import { EsriMapService } from '../_services/esri-map.service';

@Component({
  selector: 'app-base-map',
  templateUrl: './base-map.component.html',
  styleUrls: ['./base-map.component.css'],
})
export class BaseMapComponent implements OnInit {
  @ViewChild('mapViewNode', { static: true }) private viewNode: ElementRef;
  mapView: MapView;
  panRequestSubscription: any;
  constructor(private mapService: EsriMapService) {}
  panMap(coordinates) {
    this.mapView.goTo(coordinates).then(() => {
      this.mapView.zoom = 18;
      setTimeout(() => {
        this.mapService.panToWonderComplete();
      }, 2000);
    });
  }

  ngOnInit(): void {
    this.panRequestSubscription = this.mapService.panRequest.subscribe(() => {
      this.panMap(this.mapService.wonderCoordinates);
    });
    const map: __esri.Map = new Map({
      basemap: 'hybrid',
      ground: 'world-elevation',
    });
    this.mapView = new MapView({
      container: this.viewNode.nativeElement,
      center: [-77.744, -8.9212],
      zoom: 6,
      map,
    });
  }
}
