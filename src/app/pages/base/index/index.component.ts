import { Component, OnInit, ViewChild } from '@angular/core';
//services
import { LayerService } from '../../../_services/geometry/layer.service';
import { AlertService } from '../../../_services/base/alert.service';
import { BaseService } from '../../../_services/base/base.service';
import { AuthenticationService } from '@app/_services/auth/authentication.service';

//widgets
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Home from '@arcgis/core/widgets/Home';
import ScaleBar from '@arcgis/core/widgets/ScaleBar';
import Expand from '@arcgis/core/widgets/Expand';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import CustomWidget from 'src/app/pages/geometry/widgets/custom-widget';
//map
import MapImageLayer from '@arcgis/core/layers/MapImageLayer';
import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
//tree
import { TreeviewItem, TreeviewComponent } from 'ngx-treeview';
import { TreeModel } from '../../../_models/base/tree-model';
import { CustomTreeItem } from '../../../helpers/custom-tree-item';
//environment
import { environment } from '../../../../environments/environment';
//popup
import PopupTemplate from '@arcgis/core/PopupTemplate';
import Popup from '@arcgis/core/widgets/Popup';
//auth
import { Router, ActivatedRoute } from '@angular/router';

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
  itemsArray: any[] = [];
  treeConfig: any;
  token: string = '';

  //change detector in dom
  @ViewChild(TreeviewComponent, { static: false })
  treeviewComponent: TreeviewComponent;
  //constructor
  constructor(
    private baseService: BaseService,
    private layerService: LayerService,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService
  ) {
    this.treeConfig = {
      hasAllCheckBox: false,
      hasFilter: false,
      hasCollapseExpand: false,
      decoupleChildFromParent: false,
      maxHeight: 800,
    };
  }
  //index init
  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 6,
    };
    this.getItems([]);
  }
  //on map init
  onMapInit({ map, view }) {
    this.map = map;
    this.addWidget(map, view);
    this.addLayers(map, view);
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
      view: view,
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
  async addLayers(map, view) {
    try {
      const initialLayers = environment.initialLayers;
      if (initialLayers.length > 0) {
        initialLayers.forEach(async (layer) => {
          if (!layer.disabled) {
            const uuid = `layer_${this.baseService.newUUID()}`;
            const mapImageLayer = new MapImageLayer({
              url: layer.url,
              id: uuid,
              visible: true,
              opacity: 1,
            });
            let newLayer = { ...layer, uuid: uuid };
            let layers = await this.layerService.getFormatLayersJson2(newLayer);
            if (
              layers !== undefined &&
              layers.layers !== undefined &&
              layers.layers !== null
            ) {
              let subLayers = [];
              layers.layers.forEach((t) => {
                subLayers.push({
                  id: t.id,
                  visible: t.defaultVisibility,
                  name: t.name,
                  type: 'map-image',
                  popupEnabled: true,
                  minScale: t.minScale,
                });
              });
              mapImageLayer
                .when(
                  (layer) => {
                    layer.sublayers = subLayers;
                    layer.sublayers.forEach((sublayer) => {
                      sublayer
                        .createFeatureLayer()
                        .then((featureLayer) => featureLayer.load())
                        .then((featureLayer) => {
                          sublayer.popupTemplate =
                            featureLayer.createPopupTemplate();
                        });
                    });

                    return layer;
                  },
                  (error) => null
                )
                .then((data) => {});
              let parentJson = {
                value: newLayer.uuid,
                text: layer.name || 'Sin nombre',
                checked: layer.disabled,
                disabled: false,
                collapsed: true,
                children: layers.json,
                legends: [],
              };
              this.getItems([parentJson]);

              map.add(mapImageLayer);
            }
          }
        });
      }
    } catch (error) {
      this.alertService.error(error, 'Error al crear capas');
    }
  }
  // add sublayers to tree
  async getItems(parentChildObj) {
    if (parentChildObj === undefined || parentChildObj === null) return;

    parentChildObj.forEach((set) => {
      this.itemsArray.push(new CustomTreeItem(set));
    });
    this.items = this.itemsArray;
  }
  //on tree selected checkbox to change visibility
  onSelectedChange(event) {
    const unCheckedItems = this.treeviewComponent.selection.uncheckedItems;
    const checkedItems = this.treeviewComponent.selection.checkedItems;
    this.selectedLayers = checkedItems.map((item) => item.value);
    this.unseLectedLayers = unCheckedItems.map((item) => item.value);

    if (this.unseLectedLayers.length > 0) {
      this.unseLectedLayers.forEach((element) => {
        let arr = element.split('_');
        const parentLayer = this.map.findLayerById(`${arr[0]}_${arr[1]}`);
        let sublayer = parentLayer.findSublayerById(Number(arr[2]));
        if (sublayer) {
          sublayer.visible = false;
        }
      });
    }
    if (this.selectedLayers.length > 0) {
      this.selectedLayers.forEach((element) => {
        let arr = element.split('_');
        const parentLayer = this.map.findLayerById(`${arr[0]}_${arr[1]}`);
        let sublayer = parentLayer.findSublayerById(Number(arr[2]));
        if (sublayer) {
          sublayer.visible = true;
        }
      });
    }
  }

  clickIcon(event) {
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    var value = idAttr.nodeValue;

    var element = document.getElementById(value);
    if (element) {
      if (element.classList.contains('bi-plus-lg')) {
        element.classList.remove('bi-plus-lg');
        element.classList.add('bi-dash-lg');
      } else {
        element.classList.remove('bi-dash-lg');
        element.classList.add('bi-plus-lg');
      }
    }
  }
  test() {
    // var layer = new MapImageLayer({
    //   url: 'https://sampleserver6.arcgisonline.com/arcgis/rest/services/USA/MapServer',
    //   sublayers: [
    //     { id: 2, visible: false, popupTemplate: null },
    //     { id: 1, visible: false, popupTemplate:  },
    //     { id: 0, visible: false, popupTemplate: workOrderPopupTemplate },
    //   ],
    // });
  }
}
