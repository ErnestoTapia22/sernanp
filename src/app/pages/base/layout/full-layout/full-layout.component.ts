import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { TreeviewItem } from 'ngx-treeview';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import { EsriMapService } from '../../../../_services/geometry/esri-map.service';
import Expand from '@arcgis/core/widgets/Expand';
import { first } from 'rxjs/operators';
import { Observable, Subject, Subscription } from 'rxjs';

@Component({
  selector: 'app-full-layout',
  templateUrl: './full-layout.component.html',
  styleUrls: ['./full-layout.component.css'],
})
export class FullLayoutComponent implements OnInit, OnDestroy {
  items: any;
  MeExpand: Expand;
  susbcription: Subscription;

  simpleItems2 = {
    text: 'parent-2',
    value: 'p2',
    collapsed: true,
    children: [
      {
        text: 'child-1',
        value: 'c1',
      },
      {
        text: 'child-2',
        value: 'c2',
        children: [
          {
            text: 'child-1-2',
            value: 'c12',
          },
          {
            text: 'child-1-2',
            value: 'c12',
            disabled: true,
            collapsed: true,
            checked: true,
            children: [
              {
                text: 'child-1-2',
                value: 'c12',
              },
              {
                text: 'child-1-2',
                value: 'c12',
              },
            ],
          },
        ],
      },
    ],
  };
  private view: any = null;
  constructor(private mapService: EsriMapService) {
    // const bmg = new BaseMapGallery({
    //   view: this.view,
    // });
    // this.MeExpand = new Expand({
    //   view: this.view,
    //   content: bmg,
    //   expanded: false,
    //   expandTooltip: 'Estilos de mapa',
    // });
  }
  status: boolean = false;
  clickEvent() {
    this.status = !this.status;
  }
  ngOnInit(): void {
    // this.loadJsFile('../../../../../assets/js/custom.js');
  }

  onSelectedChange(event) {
    // console.log(event);
  }

  loadMap(): void {}
  ngOnDestroy() {}
  getItems(parentChildObj) {
    let itemsArray = [];
    parentChildObj.forEach((set) => {
      itemsArray.push(new TreeviewItem(set));
    });
    return itemsArray;
  }
  // loadJsFile(url) {
  //   let node = document.createElement('script');
  //   node.src = url;
  //   node.type = 'text/javascript';
  //   document.getElementsByTagName('head')[0].appendChild(node);
  // }
}
