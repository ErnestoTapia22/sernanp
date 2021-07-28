import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { TreeviewItem } from 'ngx-treeview';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import { EsriMapService } from '../../../_services/esri-map.service';
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
  simpleItems = {
    text: 'parent-1',
    value: 'p1',
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
    this.items = this.getItems([this.simpleItems, this.simpleItems2]);
    // this.loadMap();
  }
  getItems(parentChildObj) {
    let itemsArray = [];
    parentChildObj.forEach((set) => {
      itemsArray.push(new TreeviewItem(set));
    });
    return itemsArray;
  }
  onSelectedChange(event) {
    // console.log(event);
  }

  loadMap(): void {}
  ngOnDestroy() {}
}
