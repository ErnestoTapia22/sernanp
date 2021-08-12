import { Component, OnInit } from '@angular/core';
import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
import FeatureForm from '@arcgis/core/widgets/FeatureForm';
import Expand from '@arcgis/core/widgets/Expand';
import Graphic from '@arcgis/core/Graphic';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
})
export class TestComponent implements OnInit {
  editFeature: Graphic;
  highlight;
  featureForm;
  editArea;
  attributeEditing;
  updateInstructionDiv;
  featureLayer: FeatureLayer;
  view;
  mapProperties: any;
  mapViewProperties: any;

  constructor() {}

  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 6,
    };
  }
  onMapInit({ map, view }) {
    this.view = view;
    this.featureLayer = new FeatureLayer({
      url:
        'https://gisem.osinergmin.gob.pe/serverdc/rest/services/GasNatural/Produccion/FeatureServer/1',
      outFields: ['*'],
      popupEnabled: true,
      id: 'featureTest',
    });
    map.add(this.featureLayer);
    this.setupEditing();
  }
  setupEditing() {
    // input boxes for the attribute editing
    this.editArea = document.getElementById('editArea');
    this.updateInstructionDiv = document.getElementById('updateInstructionDiv');
    this.attributeEditing = document.getElementById('featureUpdateDiv');
    // Cree una nueva tabla de entidades y configure su capa featureLayer, o muestre los atributos del campo especificado en fieldConfig
    this.featureForm = new FeatureForm({
      container: 'formDiv',
      layer: this.featureLayer,
      fieldConfig: [
        {
          name: 'NOMCAM',
          label: 'Nombre del campamento',
        },
        {
          name: 'UTMX',
          label: 'ESTE',
        },
        {
          name: 'UTMY',
          label: 'NORTE',
        },
        {
          name: 'EMPRESA',
          label: 'Empresa',
        },
        {
          name: 'USUARIOCREADOR',
          label: 'Usuario creador',
        },
      ],
    });

    const editExpand = new Expand({
      expandIconClass: 'esri-icon-edit',
      expandTooltip: 'Expand Edit',
      expanded: false,
      view: this.view,
      content: this.editArea,
    });
    this.view.ui.add(editExpand, 'top-left');
  }
  applyEdits(params) {
    let thiss = this;
    this.unselectFeature();
    this.featureLayer
      .applyEdits(params)
      .then(function (editsResult) {
        // Obtenga el objectId del elemento recién agregado, llame a la función selectFeature para resaltar el elemento modificado
        if (editsResult.addFeatureResults.length > 0) {
          const objectId = editsResult.addFeatureResults[0].objectId;
          thiss.selectFeature(objectId);
        }
      })
      .catch(function (error) {
        console.log('===============================================');
        console.error(
          '[ applyEdits ] FAILURE: ',
          error.code,
          error.name,
          error.message
        );
        console.log('error = ', error);
      });
  }
  update() {
    let thiss = this;
    // Activar evento de envío de tabla de elementos
    this.featureForm.on('submit', function () {
      if (thiss.editFeature) {
        // Obtenga los atributos actualizados en la tabla, el tipo es objeto
        const updated = thiss.featureForm.getValues();
        // Recorre los atributos actualizados y asigna los atributos actualizados a los atributos de entidad vectorial
        Object.keys(updated).forEach(function (name) {
          thiss.editFeature.attributes[name] = updated[name];
        });

        // Setup the applyEdits parameter with updates.
        const edits = {
          updateFeatures: [thiss.editFeature],
        };
        thiss.applyEdits(edits);
      }
    });
    this.featureForm.submit();
  }
  delete() {
    // console.log(event);
    const edits = {
      deleteFeatures: [this.editFeature],
    };
    this.applyEdits(edits);
  }
  addFeature() {
    // debugger;
    this.unselectFeature();
    let thiss = this;
    const handler = this.view.on('click', function (event) {
      handler.remove();
      event.stopPropagation();

      if (event.mapPoint) {
        let point = event.mapPoint.clone();
        point.z = undefined;
        point.hasZ = false;

        // Crea una estrella de hotel como elemento económico
        thiss.editFeature = new Graphic({
          geometry: point,
          attributes: {
            hotelstar: 'Economía',
          },
        });

        const edits = {
          addFeatures: [thiss.editFeature],
        };
        thiss.applyEdits(edits);
        document.getElementById('mapViewNode').style.cursor = 'auto';
      } else {
        console.error('event.mapPoint is not defined');
      }
    });
    document.getElementById('mapViewNode').style.cursor = 'crosshair';
    this.editArea.style.cursor = 'auto';
  }
  unselectFeature() {
    this.attributeEditing.style.display = 'none';
    this.updateInstructionDiv.style.display = 'block';
    this.featureForm.feature = null;
    if (this.highlight) {
      this.highlight.remove();
    }
  }
  selectFeature(objectId) {
    let thiss = this;
    // query feature from the server
    this.featureLayer
      .queryFeatures({
        objectIds: [objectId],
        outFields: ['*'],
        returnGeometry: true,
      })
      .then(function (results) {
        if (results.features.length > 0) {
          thiss.editFeature = results.features[0];

          // Muestra los atributos de los elementos seleccionados en la tabla
          thiss.featureForm.feature = thiss.editFeature;

          // Destacar elementos en la vista
          thiss.view
            .whenLayerView(thiss.editFeature.layer)
            .then(function (layerView) {
              thiss.highlight = layerView.highlight(thiss.editFeature);
            });

          thiss.attributeEditing.style.display = 'block';
          thiss.updateInstructionDiv.style.display = 'none';
        }
      });
  }
}
