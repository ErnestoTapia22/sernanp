import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  OnDestroy,
  NgZone,
  Input,
  Output,
  EventEmitter,
} from '@angular/core';
import MapView from '@arcgis/core/views/MapView';
import Map from '@arcgis/core/Map';
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Expand from '@arcgis/core/widgets/Expand';
import WebMap from '@arcgis/core/WebMap';
import config from '@arcgis/core/config';
import { EsriMapService } from '../_services/esri-map.service';

@Component({
  selector: 'app-base-map',
  templateUrl: './base-map.component.html',
  styleUrls: ['./base-map.component.css'],
})
export class BaseMapComponent implements OnInit, OnDestroy {
  @ViewChild('mapViewNode', { static: true }) private viewNode!: ElementRef;
  @Input() mapProperties!: any;
  @Input() mapViewProperties!: any;
  @Output() mapInit: EventEmitter<any> = new EventEmitter();

  private view: any = null;

  constructor(private zone: NgZone, private mapService: EsriMapService) {}
  initializeMap(): Promise<any> {
    const container = this.viewNode;
    const map: Map = new Map({
      basemap: 'hybrid',
      ground: 'world-elevation',
    });
    const mapView = new MapView({
      container: this.viewNode.nativeElement,
      center: [-77.744, -8.9212],
      zoom: 6,
      map,
    });

    const bookMarks = new BookMarks({
      view: mapView,
      editingEnabled: true,
    });
    const bkExpand = new Expand({
      view: mapView,
      content: bookMarks,
      expanded: false,
    });
    mapView.ui.add(bkExpand, 'bottom-trailing');
    this.view = mapView;
    return this.view.when();
  }
  ngOnInit(): void {
    // config.assetsPath='assets/'

    this.initializeMap().then(() => {
      console.log('the map is ready');
    });
    // this.zone.runOutsideAngular(()=>{
    //   this.loadMap()
    // })
  }
  ngOnDestroy(): void {
    if (this.view) {
      // destroy the map view
      this.view.destroy();
    }
  }
  loadMap(): void {
    this.mapService.isLoaded.subscribe((n: any) => {
      this.view = n.view;
      this.zone.run(() => {
        this.mapInit.emit({ map: n.map, view: n.view });
        this.mapInit.complete();
      });
    });
    this.mapService.loadMap({
      ...this.mapProperties,
      ...this.mapViewProperties,
      container: this.viewNode.nativeElement,
    });
  }
}
