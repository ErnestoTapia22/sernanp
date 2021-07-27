import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import BookMarks from '@arcgis/core/widgets/Bookmarks';
import Expand from '@arcgis/core/widgets/Expand';
import Measurement from '@arcgis/core/widgets/Measurement';
import BaseMapGallery from '@arcgis/core/widgets/BasemapGallery';
import Basemap from '@arcgis/core/Basemap';
import { BaseWidgetComponent } from '../../widgets/base-widget/base-widget.component';
import CustomWidget from 'src/app/widgets/custom-widget';
import { expand } from 'rxjs/operators';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css'],
})
export class IndexComponent implements OnInit {
  mapProperties: any;
  mapViewProperties: any;
  @ViewChild('test', { static: true }) private viewNode!: ElementRef;
  constructor() {}

  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-77.744, -8.9212],
      zoom: 6,
    };
  }

  onMapInit({ map, view }) {
    const bookMarks = new BookMarks({
      view: view,
      editingEnabled: true,
    });
    const bkExpand = new Expand({
      view: view,
      content: bookMarks,
      expanded: false,
    });
    const customW = new CustomWidget((map = map), (view = view));
    const bmg = new BaseMapGallery({
      view: view,
    });
    const MeExpand = new Expand({
      view: view,
      content: bmg,
      expanded: false,
      expandTooltip: 'Estilos de mapa',
    });
    const cw = new Expand({
      view: view,
      content: customW,
      expanded: false,
      expandTooltip: 'Custom widget',
    });

    view.ui.add(bkExpand, 'bottom-trailing');
    view.ui.add(MeExpand, 'bottom-trailing');
    view.ui.add(cw, 'bottom-trailing');
  }
}
