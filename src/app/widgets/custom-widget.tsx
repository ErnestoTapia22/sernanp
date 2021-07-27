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
        style="z-index:1;display:flex;cursor:move;position: relative"
        class="esri-widget--panel esri-widget"
        cdkDrag
      >
        is simply dummy text of the printing and typesetting industry. Lorem
        Ipsum has been the industry's standard dummy text ever since the 1500s,
        when an unknown printer took a galley of type and scrambled it to make a
        type specimen book. It has survived not only five centuries, but also
        the leap into electronic typesetting, remaining essentially unchanged.
        It was popularised in the 1960s with the release of Letraset sheets
        containing Lorem Ipsum passages, and more recently with desktop
        publishing software like Aldus PageMaker including versions of Lorem
        Ipsum.
      </div>
    );
  }
}
