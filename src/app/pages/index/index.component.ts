import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { BaseService } from '../../_services/base.service';
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Expand from '@arcgis/core/widgets/Expand';
import Measurement from '@arcgis/core/widgets/Measurement';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import Basemap from '@arcgis/core/Basemap';
import MapImageLayer from '@arcgis/core/layers/MapImageLayer';
import { BaseWidgetComponent } from '../../widgets/base-widget/base-widget.component';
import CustomWidget from 'src/app/widgets/custom-widget';
import { expand } from 'rxjs/operators';
import { LayerService } from '../../_services/layer.service';
import { AlertService } from '../../_services/alert.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  mapProperties: any;
  mapViewProperties: any;
  @ViewChild('test', { static: true }) private viewNode!: ElementRef;
  constructor(
    private baseService: BaseService,
    private layerService: LayerService,
    private alertService: AlertService
  ) {}

  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-77.744, -8.9212],
      zoom: 6,
    };
    // this.getFormatLayers();
    this.getFormatLayers2();
  }

  onMapInit({ map, view }) {
    this.addWidget(map, view);
    this.addLayers(map);
  }
  addWidget(map, view) {
    //create widgets
    const customW = new CustomWidget((map = map), (view = view));
    const bmg = new BaseMapGallery({
      view: view,
    });
    const bookMarks = new BookMarks({
      view: view,
      editingEnabled: true,
    });
    //expands
    const bkExpand = new Expand({
      view: view,
      content: bookMarks,
      expanded: false,
    });
    const cw = new Expand({
      view: view,
      content: customW,
      expanded: false,
      expandTooltip: 'Custom widget',
    });
    const MeExpand = new Expand({
      view: view,
      content: bmg,
      expanded: false,
      expandTooltip: 'Estilos de mapa',
    });
    //adds
    view.ui.add(MeExpand, 'bottom-trailing');
    view.ui.add(bkExpand, 'bottom-trailing');
    view.ui.add(cw, 'bottom-trailing');
  }
  getFormatLayers(): void {
    this.layerService.getInitialLayersJson().subscribe(
      (response) => {
        console.log(response);
        // this.alertService.success('Todo ok', 'Funciono', { autoClose: false });
      },
      (error) => {
        console.log(error.errors.message);
        this.alertService.error(error.errors.message, 'Error en el servicio');
      }
    );
  }
  addLayers(map) {
    try {
      const mapImageLayer = new MapImageLayer({
        url:
          'https://gisem.osinergmin.gob.pe/serverdc/rest/services/DSGN/SCADA/MapServer',
        id: this.baseService.newUUID(),
        visible: true,
        opacity: 1,
      });
      let sublayers = [];
      sublayers.push({
        id: 0,
        text: 'test1',
        value: 0,
        children: [],
      });
      mapImageLayer.when((layer) => {
        layer.sublayers = sublayers;
      });
      map.add(mapImageLayer);
      console.log(map.allLayers);
    } catch (error) {
      this.alertService.error(error.error.message, 'Error al crear capas');
    }
  }
  getFormatLayers2() {
    console.log(this.layerService.getFormatLayersJson());
  }
}
