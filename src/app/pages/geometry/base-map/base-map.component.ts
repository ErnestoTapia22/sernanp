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
// import MapView from '@arcgis/core/views/MapView';
// import Map from '@arcgis/core/Map';
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Expand from '@arcgis/core/widgets/Expand';
// import WebMap from '@arcgis/core/WebMap';
import config from '@arcgis/core/config';
import { EsriMapService } from '../../../_services/geometry/esri-map.service';

@Component({
  selector: 'app-base-map',
  templateUrl: './base-map.component.html',
  styleUrls: ['./base-map.component.css'],
})
export class BaseMapComponent implements OnInit, OnDestroy {
  setMaxHeight: number = 0;
  @ViewChild('mapViewNode', { static: true }) private viewNode!: ElementRef;
  @Input() mapProperties!: any;
  @Input() mapViewProperties!: any;
  @Input() maxHeight?: any;
  @Output() mapInit: EventEmitter<any> = new EventEmitter();

  private view: any = null;

  constructor(private zone: NgZone, private mapService: EsriMapService) {}

  ngOnInit(): void {
    // config.assetsPath='assets/'

    // this.initializeMap().then(() => {
    //   console.log('the map is ready');
    // });
    this.zone.runOutsideAngular(() => {
      this.loadMap();
    });
    this.setMaxHeight = this.maxHeight;
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
