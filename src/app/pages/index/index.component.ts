import { Component, OnInit, ViewChild } from '@angular/core';
//services
import { LayerService } from '../../_services/layer.service';
import { AlertService } from '../../_services/alert.service';
import { BaseService } from '../../_services/base.service';
//widgets
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Home from '@arcgis/core/widgets/Home';
import ScaleBar from '@arcgis/core/widgets/ScaleBar';
import Expand from '@arcgis/core/widgets/Expand';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import CustomWidget from 'src/app/widgets/custom-widget';
//map
import MapImageLayer from '@arcgis/core/layers/MapImageLayer';
//tree
import { TreeviewItem, TreeviewComponent } from 'ngx-treeview';
//environment
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  //variables
  mapProperties: any;
  mapViewProperties: any;
  items: any[] = [];

  selectedLayers: any;
  unseLectedLayers: any;
  map: any;
  view: any;
  parentLayer: MapImageLayer;

  //change detector in dom
  @ViewChild(TreeviewComponent, { static: false })
  treeviewComponent: TreeviewComponent;
  //constructor
  constructor(
    private baseService: BaseService,
    private layerService: LayerService,
    private alertService: AlertService
  ) {}
  //index init
  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-77.744, -8.9212],
      zoom: 6,
    };
    this.getItems([]);
  }
  //on map init
  onMapInit({ map, view }) {
    this.map = map;
    this.addWidget(map, view);
    this.addLayers(map);
  }
  //create widgets
  addWidget(map, view) {
    const customW = new CustomWidget((map = map), (view = view));
    const bmg = new BaseMapGallery({
      view: view,
    });
    const bookMarks = new BookMarks({
      view: view,
      editingEnabled: true,
    });
    const home = new Home({
      view: view
    });
    const scaleBar = new ScaleBar({
      view: view,
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
    view.ui.add(MeExpand, 'top-left');
    view.ui.add(bkExpand, 'top-left');
    view.ui.add(cw, 'top-left');
    view.ui.add(home, 'top-left');
    view.ui.add(scaleBar, 'bottom-left');
  }
  //add initial layers
  async addLayers(map) {
    try {
      const initialLayers = environment.initialLayers;
      if (initialLayers.length > 0) {
        initialLayers.forEach(async (layer) => {
          if (!layer.disabled) {
            const mapImageLayer = new MapImageLayer({
              url: layer.url,
              id: 'layerMain',
              visible: true,
              opacity: 1
            });
            let layers = await this.layerService.getFormatLayersJson2(layer);
            let subLayers = [];
            layers.layers.forEach(t => {
                subLayers.push({ id: t.id, popupTemplate: null, visible: t.defaultVisibility, name: t.name, type: "map-image", minScale: t.minScale });
            });
            mapImageLayer.when(layer => {
                layer.sublayers = subLayers;
                return layer;
            }, error => null).then(data => {
            });
            this.getItems(layers.json);
            map.add(mapImageLayer);
          }
        });
      }
    } catch (error) {
      this.alertService.error(error, 'Error al crear capas');
    }
  }
  // add sublayers to tree
  async getItems(parentChildObj) {
    if (parentChildObj === undefined) return [];
    let itemsArray = [];

    parentChildObj.forEach((set) => {
      itemsArray.push(new TreeviewItem(set));
    });
    this.items = itemsArray;
  }
  //on tree selected checkbox to change visibility
  onSelectedChange(event) {
    const unCheckedItems = this.treeviewComponent.selection.uncheckedItems;
    const checkedItems = this.treeviewComponent.selection.checkedItems;
    this.selectedLayers = checkedItems.map((item) => item.value);
    this.unseLectedLayers = unCheckedItems.map((item) => item.value);

    const parentLayer = this.map.findLayerById('layerMain'); //parent layer
    if (parentLayer) {
      if (this.unseLectedLayers.length > 0) {
        this.unseLectedLayers.forEach((element) => {
          let subLayerUnchecked = parentLayer.findSublayerById(element);

          if (subLayerUnchecked) {
            subLayerUnchecked.visible = false;
          }
        });
        this.selectedLayers.forEach((element) => {
          let subLayerChecked = parentLayer.findSublayerById(element);
          if (subLayerChecked) {
            subLayerChecked.visible = true;
          }
        });
      }
    }
  }
}
