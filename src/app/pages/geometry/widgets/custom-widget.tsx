import Widget from "@arcgis/core/widgets/Widget";
import {
  subclass,
  property,
} from "@arcgis/core/core/accessorSupport/decorators";
import { tsx } from "@arcgis/core/widgets/support/widget";
import MapView from "@arcgis/core/views/MapView";
import Map from "@arcgis/core/Map";

@subclass("esri.widgets.SimpleWidget")
export default class CustomWidget extends Widget {
  constructor(map: Map, view: MapView) {
    super();
  }
  @property()
  enabled: boolean = false;
  @property()
  view: MapView;

  render() {
    return (
      <div
        style="z-index:1;display:flex;position: relative; width: 100px;height:100px;"
        class="esri-widget--panel esri-widget"
      >
        <div style="width:30px;,height:30px;background-color:red" cdkDrag>
          ASDASDASD
        </div>
      </div>
    );
  }
}
