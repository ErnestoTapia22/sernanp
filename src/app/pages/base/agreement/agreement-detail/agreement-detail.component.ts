import { Component, OnInit } from '@angular/core';
import { AgreementService } from '@app/_services/base/agreement.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { Router, ActivatedRoute } from '@angular/router';

//esri
import esriConfig from '@arcgis/core/config';
import Expand from '@arcgis/core/widgets/Expand';
import Request from '@arcgis/core/request';
import FeatureLayer from '@arcgis/core/layers/FeatureLayer';
import Field from '@arcgis/core/layers/support/Field';
import Graphic from '@arcgis/core/Graphic';
import MapView from '@arcgis/core/views/MapView';
import Map from '@arcgis/core/Map';
//services
import { AlertService } from '@app/_services/base/alert.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-agreement-detail',
  templateUrl: './agreement-detail.component.html',
  styleUrls: ['./agreement-detail.component.css'],
})
export class AgreementDetailComponent implements OnInit {
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
  agreementExist: boolean = false;
  agreementStateList: any[] = [];
  submitted: boolean = false;
  agreementId;

  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router
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
    this.fillSelects();
    if (
      this.route.snapshot.paramMap.get('id') !== undefined &&
      this.route.snapshot.paramMap.get('id') !== null &&
      this.route.snapshot.paramMap.get('id') !== ''
    ) {
      this.agreementId = parseInt(this.route.snapshot.paramMap.get('id'));
      this.getDetail(this.agreementId);
    }
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
      this.agreementService.agreementStateList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          console.log(response);
          this.agreementStateList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error(
        'Error al traer la lista de estados del acuerdo',
        'error',
        { autoClose: true }
      );
    }
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
      id: [0],
      name: [''],
      description: [''],
      state: [true],
      registrationDate: [''],
      code: [''],
      vigency: [0],
      firm: [''],
      partMen: [0],
      partWomen: [0],
      benPerson: [''],
      benIndirect: [''],
      numFamily: [0],
      benFamily: [''],
      producedArea: [0],
      detalleProduction: [''],

      agreementState: this.fb.group({
        id: 0,
      }),
      anp: this.fb.group({
        id: 0,
      }),
      areaAmbitc: [{ value: 0, disabled: true }],
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
  updateAgreement() {
    try {
      return;
      this.submitted = true;
      console.log(this.form.value);

      if (this.form.invalid) {
        console.log(this.form.invalid);
        return;
      }
      this.agreementService
        .agreementInsert(JSON.stringify(this.form.value))
        .subscribe((response) => {
          if (response && response.success === true) {
            this.submitted = false;
            this.agreementExist = true;
            this.getWorkPlan(response.extra);
            this.alertService.success(
              'Se registro correctamenteel acuerdo',
              'Ok',
              { autoClose: true }
            );
          } else {
            this.alertService.error('error: ' + response.message, 'error', {
              autoClose: true,
            });
          }
        });
    } catch (error) {
      this.alertService.error('Error al guardar el acuerdo', 'Error', {
        autoClose: true,
      });
    }
  }
  getWorkPlan(id) {
    console.log(id);
  }
  getDetail(id) {
    if (id === null && id === undefined && id === 0) {
      this.alertService.error('No se encontro el id del acuerdo', 'Error', {
        autoClose: true,
      });
      return;
    }
    try {
      this.agreementService.agreementDetail(id).subscribe((response) => {
        if (response && response.item !== null) {
          console.log(response);
          this.form.setValue({
            id: response.item.id,
            name: response.item.name,
            description: response.item.description,
            state: response.item.state,
            registrationDate: response.item.registrationDate,
            code: response.item.code,
            vigency: response.item.vigency,
            firm: response.item.firm,
            partMen: response.item.partMen,
            partWomen: response.item.partWomen,
            benPerson: response.item.benPerson,
            benIndirect: response.item.benIndirect,
            numFamily: response.item.benIndirect,
            benFamily: response.item.benFamily,
            areaAmbitc: response.item.areaAmbitc,
            producedArea: response.item.producedArea,
            detalleProduction: response.item.detalleProduction,
            agreementState: { id: response.item.agreementState.id || 0 },
            anp: { id: 0 },
          });
        }
      });
    } catch (error) {
      this.alertService.error(
        'Error al traer el detalle del acuerdo',
        'Error',
        { autoClose: true }
      );
    }
  }
}
