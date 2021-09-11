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
//services
import { AlertService } from '@app/_services/base/alert.service';
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
  agreementExist: boolean = false;
  agreementStateList: any[] = [];
  submitted: boolean = false;
  attributes: object = {
    // codigo: '',
    // nombre: '',
    // areatotal: 0,
    // comunidad: '',
    // anexo: '',
    // productor: '',
    EMPRESA: 'prueba9112021',
    NOMCAM: 'TEST',
    UTMX: 20.3,
    UTMY: 20.4,
    USUARIOCREADOR: 'D',
  };
  graphics: any[] = [];
  esriJsons: Graphic[] = [];
  featureLayer: FeatureLayer;

  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder,
    private alertService: AlertService
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
    // this.featureLayer = new FeatureLayer({
    //   url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/GasNatural/Produccion/FeatureServer/1',
    //   outFields: ['*'],
    //   popupEnabled: true,
    //   id: 'featureTest',
    // });
    // map.add(this.featureLayer);
  }
  eventListener() {
    document
      .getElementById('uploadForm')
      .addEventListener('change', (event) => {
        const fileName = (event.target as HTMLFormElement).value.toLowerCase();
        const htmlStatus = document.getElementById('upload-status');
        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user

          const htmlForm = document.getElementById(
            'uploadForm'
          ) as HTMLFormElement;
          this.generateFeatureCollection(fileName, htmlStatus, htmlForm);
        } else {
          htmlStatus.innerHTML =
            '<p style="color:red">Add shapefile as .zip file</p>';
        }
      });
    document
      .getElementById('uploadForm2')
      .addEventListener('change', (event) => {
        const fileName = (event.target as HTMLFormElement).value.toLowerCase();
        const htmlStatus = document.getElementById('upload-status2');
        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user

          const htmlForm = document.getElementById(
            'uploadForm2'
          ) as HTMLFormElement;
          this.generateFeatureCollection(fileName, htmlStatus, htmlForm);
        } else {
          htmlStatus.innerHTML =
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
      expandTooltip: 'área del ámbito del acuerdo de conservación',
      group: 'fileform',
      id: 'fileform',
    });
    const fileForm2 = document.getElementById('mainWindow2');
    const expand2 = new Expand({
      expandIconClass: 'esri-icon-polygon',
      view: this.view,
      content: fileForm2,
      expandTooltip: 'área de vigilancia del acuerdo de conservación',
      group: 'fileform',
      id: 'fileform2',
    });
    this.view.ui.add(expand, 'top-right');
    this.view.ui.add(expand2, 'top-right');
  }

  generateFeatureCollection(
    fileName,
    htmlStatus?: HTMLElement,
    hmtlForm?: HTMLFormElement
  ) {
    let name = fileName.split('.');
    // Chrome adds c:\fakepath to the value - we need to remove it
    name = name[0].replace('c:\\fakepath\\', '');

    htmlStatus.innerHTML = '<b>Loading </b>' + name;

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
      body: hmtlForm,
      responseType: 'json',
    })
      .then((response) => {
        console.log(response);
        const layerName =
          response.data.featureCollection.layers[0].layerDefinition.name;
        htmlStatus.innerHTML = '<b>Loaded: </b>' + layerName;
        this.addShapefileToMap(response.data.featureCollection, htmlStatus);
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
  addShapefileToMap(featureCollection, htmlStatus?: HTMLElement) {
    // add the shapefile to the map and zoom to the feature collection extent
    // if you want to persist the feature collection when you reload browser, you could store the
    // collection in local storage by serializing the layer using featureLayer.toJson()
    // see the 'Feature Collection in Local Storage' sample for an example of how to work with local storage
    let sourceGraphics = [];

    const layers = featureCollection.layers.map((layer) => {
      const graphics = layer.featureSet.features.map((feature) => {
        return Graphic.fromJSON(feature);
      });
      const geometry = layer.featureSet.features.map((feature) => {
        return feature.geometry;
      });
      console.log(geometry);
      this.graphics.push(geometry);
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

    htmlStatus.innerHTML = '';
  }

  errorHandler(error) {
    this.alertService.error(
      'Error al subir shapefile :' + error.message,
      'Error',
      { autoClose: true }
    );
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
      areaAmbitC: [0],
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
  insertAgreement() {
    try {
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
            this.buildAttributes();
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
  addFeatureToService() {
    this.buildEsriJson();
    console.log(this.esriJsons);
    if (this.esriJsons.length === 0) {
      return;
    }
    const edits = {
      addFeatures: this.esriJsons,
    };
    console.log(edits);

    this.featureLayer = new FeatureLayer({
      url: 'https://gisem.osinergmin.gob.pe/serverdc/rest/services/GasNatural/Produccion/FeatureServer/1',
      outFields: ['*'],
      popupEnabled: true,
      id: 'featureTest',
    });
    this.featureLayer
      .applyEdits(edits)
      .then(function (editsResult) {
        console.log(editsResult);
        if (editsResult.addFeatureResults.length > 0) {
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
  buildAttributes() {
    // this.attributes
  }
  buildEsriJson() {
    for (let i = 0; i < 4; i++) {
      if (this.graphics[i] === undefined || this.graphics[i] === null) {
        continue;
      }
      console.log(this.graphics[i][0]);
      this.esriJsons.push(
        Graphic.fromJSON({
          attributes: this.attributes,
          geometry: this.graphics[i][0],
        })
      );
    }
  }
}
