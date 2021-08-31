import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
//esri
import esriConfig from '@arcgis/core/config';
import Expand from '@arcgis/core/widgets/Expand';
import Request from '@arcgis/core/request';
import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
import Field from '@arcgis/core/layers/support/Field';
import Graphic from '@arcgis/core/Graphic';
import MapView from '@arcgis/core/views/MapView';
import Map from '@arcgis/core/Map';

@Component({
  selector: 'app-agreement-new',
  templateUrl: './agreement-new.component.html',
  styleUrls: ['./agreement-new.component.css'],
})
export class AgreementNewComponent implements OnInit {
  selectedAnp: number = 0;
  obsQuery = new BehaviorSubject({ item: '' });
  anp: Object[] = [];
  form: FormGroup;
  mapProperties: any;
  mapViewProperties: any;
  fieldArray: Array<any> = [];
  newAttribute: any = {};

  portalUrl = 'https://www.arcgis.com';
  view: MapView;
  map: Map;
  request: Request;
  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 9,
    };
    this.buildForm();
    let item = {
      name: '',
    };
    this.obsQuery.next({
      item: JSON.stringify(item),
    });
  }
  async readURL(event) {}

  async readFile(file) {
    const arrayBuffer = await new Promise((resolve) => {
      const reader = new FileReader();
      // reader.onload = () => resolve(reader.result);

      reader.onload = () => resolve(reader.result);
      reader.readAsArrayBuffer(file);
    });

    return arrayBuffer;
  }
  onMapInit({ map, view }) {
    this.map = map;
    this.view = view;
    this.addExpand();
    this.eventListener();
  }
  eventListener() {
    document
      .getElementById('uploadForm')
      .addEventListener('change', (event) => {
        const fileName = (event.target as HTMLFormElement).value.toLowerCase();

        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user
          this.generateFeatureCollection(fileName);
        } else {
          document.getElementById('upload-status').innerHTML =
            '<p style="color:red">Add shapefile as .zip file</p>';
        }
      });
    document
      .getElementById('uploadForm2')
      .addEventListener('change', (event) => {
        const fileName = (event.target as HTMLFormElement).value.toLowerCase();

        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user
          this.generateFeatureCollection2(fileName);
        } else {
          document.getElementById('upload-status2').innerHTML =
            '<p style="color:red">Add shapefile as .zip file</p>';
        }
      });
  }
  addExpand() {
    const fileForm = document.getElementById('mainWindow');
    const expand = new Expand({
      expandIconClass: 'esri-icon-upload',
      view: this.view,
      content: fileForm,
    });
    const fileForm2 = document.getElementById('mainWindow2');
    const expand2 = new Expand({
      expandIconClass: 'esri-icon-polygon',
      view: this.view,
      content: fileForm2,
    });
    this.view.ui.add(expand, 'top-right');
    this.view.ui.add(expand2, 'top-right');
  }
  generateFeatureCollection2(fileName) {
    let thiss = this;
    let name = fileName.split('.');
    // Chrome adds c:\fakepath to the value - we need to remove it
    name = name[0].replace('c:\\fakepath\\', '');

    document.getElementById('upload-status2').innerHTML =
      '<b>Loading </b>' + name;

    // define the input params for generate see the rest doc for details
    // https://developers.arcgis.com/rest/users-groups-and-items/generate.htm
    const params = {
      name: name,
      targetSR: this.view.spatialReference,
      maxRecordCount: 1000,
      enforceInputFileSizeLimit: true,
      enforceOutputJsonSizeLimit: true,
      generalize: true,
      maxAllowableOffset: 10,
      reducePrecision: true,
      numberOfDigitsAfterDecimal: 0,
    };

    // generalize features to 10 meters for better performance
    // params.generalize = true;
    // params.maxAllowableOffset = 10;
    // params.reducePrecision = true;
    // params.numberOfDigitsAfterDecimal = 0;

    const myContent = {
      filetype: 'shapefile',
      publishParameters: JSON.stringify(params),
      f: 'json',
    };

    // use the REST generate operation to generate a feature collection from the zipped shapefile
    Request(this.portalUrl + '/sharing/rest/content/features/generate', {
      query: myContent,
      body: document.getElementById('uploadForm2') as HTMLFormElement,
      responseType: 'json',
    })
      .then((response) => {
        console.log(response);
        const layerName =
          response.data.featureCollection.layers[0].layerDefinition.name;
        document.getElementById('upload-status2').innerHTML =
          '<b>Loaded: </b>' + layerName;
        this.addShapefileToMap2(response.data.featureCollection);
      })
      .catch(this.errorHandler2);
  }
  generateFeatureCollection(fileName) {
    let thiss = this;
    let name = fileName.split('.');
    // Chrome adds c:\fakepath to the value - we need to remove it
    name = name[0].replace('c:\\fakepath\\', '');

    document.getElementById('upload-status').innerHTML =
      '<b>Loading </b>' + name;

    // define the input params for generate see the rest doc for details
    // https://developers.arcgis.com/rest/users-groups-and-items/generate.htm
    const params = {
      name: name,
      targetSR: this.view.spatialReference,
      maxRecordCount: 1000,
      enforceInputFileSizeLimit: true,
      enforceOutputJsonSizeLimit: true,
      generalize: true,
      maxAllowableOffset: 10,
      reducePrecision: true,
      numberOfDigitsAfterDecimal: 0,
    };

    // generalize features to 10 meters for better performance
    // params.generalize = true;
    // params.maxAllowableOffset = 10;
    // params.reducePrecision = true;
    // params.numberOfDigitsAfterDecimal = 0;

    const myContent = {
      filetype: 'shapefile',
      publishParameters: JSON.stringify(params),
      f: 'json',
    };

    // use the REST generate operation to generate a feature collection from the zipped shapefile
    Request(this.portalUrl + '/sharing/rest/content/features/generate', {
      query: myContent,
      body: document.getElementById('uploadForm') as HTMLFormElement,
      responseType: 'json',
    })
      .then((response) => {
        console.log(response);
        const layerName =
          response.data.featureCollection.layers[0].layerDefinition.name;
        document.getElementById('upload-status').innerHTML =
          '<b>Loaded: </b>' + layerName;
        this.addShapefileToMap(response.data.featureCollection);
      })
      .catch(this.errorHandler);
  }
  get f() {
    return this.form.controls;
  }
  fillSelects() {
    try {
      if (
        environment.externalServices[0].agreement[0].url !== undefined &&
        environment.externalServices[0].agreement[0].url !== null
      ) {
        this.agreementService
          .getServices(`${environment.externalServices[0].agreement[0].url}`)
          .subscribe((response) => {
            console.log(response);
            if (response) {
              this.anp = response;
            }
          });
      }
    } catch (error) {}
  }
  addShapefileToMap(featureCollection) {
    // add the shapefile to the map and zoom to the feature collection extent
    // if you want to persist the feature collection when you reload browser, you could store the
    // collection in local storage by serializing the layer using featureLayer.toJson()
    // see the 'Feature Collection in Local Storage' sample for an example of how to work with local storage
    let sourceGraphics = [];

    const layers = featureCollection.layers.map((layer) => {
      const graphics = layer.featureSet.features.map((feature) => {
        return Graphic.fromJSON(feature);
      });
      sourceGraphics = sourceGraphics.concat(graphics);
      const featureLayer = new FeatureLayer({
        objectIdField: 'FID',
        source: graphics,
        fields: layer.layerDefinition.fields.map((field) => {
          return Field.fromJSON(field);
        }),
      });
      return featureLayer;
      // associate the feature with the popup on click to enable highlight and zoom to
    });
    this.map.addMany(layers);
    this.view.goTo(sourceGraphics).catch((error) => {
      if (error.name != 'AbortError') {
        console.error(error);
      }
    });

    document.getElementById('upload-status').innerHTML = '';
  }
  addShapefileToMap2(featureCollection) {
    // add the shapefile to the map and zoom to the feature collection extent
    // if you want to persist the feature collection when you reload browser, you could store the
    // collection in local storage by serializing the layer using featureLayer.toJson()
    // see the 'Feature Collection in Local Storage' sample for an example of how to work with local storage
    let sourceGraphics = [];

    const layers = featureCollection.layers.map((layer) => {
      const graphics = layer.featureSet.features.map((feature) => {
        return Graphic.fromJSON(feature);
      });
      sourceGraphics = sourceGraphics.concat(graphics);
      const featureLayer = new FeatureLayer({
        objectIdField: 'FID',
        source: graphics,
        fields: layer.layerDefinition.fields.map((field) => {
          return Field.fromJSON(field);
        }),
      });
      return featureLayer;
      // associate the feature with the popup on click to enable highlight and zoom to
    });
    this.map.addMany(layers);
    this.view.goTo(sourceGraphics).catch((error) => {
      if (error.name != 'AbortError') {
        console.error(error);
      }
    });

    document.getElementById('upload-status2').innerHTML = '';
  }
  errorHandler(error) {
    document.getElementById('upload-status').innerHTML =
      "<p style='color:red;max-width: 500px;'>" + error.message + '</p>';
  }
  errorHandler2(error) {
    document.getElementById('upload-status2').innerHTML =
      "<p style='color:red;max-width: 500px;'>" + error.message + '</p>';
  }
  buildForm() {
    this.form = this.fb.group({
      code: new FormControl(),
      anp: new FormControl(),
      goal: new FormControl(),
    });
  }
  addFieldValue() {
    console.log(this.newAttribute);
    this.fieldArray.push(this.newAttribute);
    this.newAttribute = {};
  }
  deleteFieldValue(index) {
    this.fieldArray.splice(index, 1);
  }
}
