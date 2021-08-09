import { Component, OnInit } from '@angular/core';
import Widget from '@arcgis/core/widgets/Widget';
import {
  subclass,
  property,
} from '@arcgis/core/core/accessorSupport/decorators';
import { tsx } from '@arcgis/core/widgets/support/widget';

@Component({
  selector: 'app-custom-widget',
  templateUrl: './custom-widget.component.html',
  styleUrls: ['./custom-widget.component.css'],
})
export class CustomWidgetComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
