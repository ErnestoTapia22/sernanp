import { Component, OnInit } from '@angular/core';
import { AgreementService } from '@app/_services/base/agreement.service';
import { MasterPlanService } from '@app/_services/masterplan/masterplan/master-plan.service';
import { AnpService } from '@app/_services/masterplan/anp/anp.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

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
import { Router, ActivatedRoute } from '@angular/router';

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
  alliedForm: FormGroup;
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
  sourceList: any[] = [];
  submitted: boolean = false;
  disabled: boolean = false;
  attributes: object = {
    codigo: '',
    nombre: '',
    areatotal: 0,
    comunidad: '',
    anexo: '',
    productor: '',
  };
  graphics: any[] = [];
  esriJsons: Graphic[] = [];
  featureLayer: FeatureLayer;
  edit: boolean = false;
  agreementId: string = '';
  modalRef: NgbModalRef;
  alliedCategoryList: any[] = [];
  alliedList: any[] = [];
  formCreateCommitments: FormGroup;
  objectiveList: any[] = [];
  anpList: any[] = [];
  anpForm: FormGroup;
  actionLineList: any[] = [];
  layerId: number = 0;
  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private masterPlanService: MasterPlanService,
    private anpService: AnpService
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
    this.agreementId = this.route.snapshot.paramMap.get('id');
    if (
      this.route.snapshot.paramMap.get('id') !== undefined &&
      this.route.snapshot.paramMap.get('id') !== null &&
      this.route.snapshot.paramMap.get('id') !== ''
    ) {
      this.agreementId = this.route.snapshot.paramMap.get('id');
      this.edit = true;
      this.getDetail(this.agreementId);
    }
    this.searchAllied();
  }
  get g() {
    return this.alliedForm.controls;
  }
  get h() {
    return this.formCreateCommitments.controls;
  }
  onAddAlliedModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'md',
      backdrop: 'static',
    });

    this.listAlliedCategory();
    this.alliedForm.patchValue({
      conservationAgreement: { id: this.agreementId },
    });
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
        const htmlStatus = document.getElementById('upload-status');
        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user

          const htmlForm = document.getElementById(
            'uploadForm'
          ) as HTMLFormElement;
          this.generateFeatureCollection(fileName, htmlStatus, htmlForm, 0);
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
          this.generateFeatureCollection(fileName, htmlStatus, htmlForm, 1);
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
    hmtlForm?: HTMLFormElement,
    layerId?: number
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
        this.addShapefileToMap(
          response.data.featureCollection,
          htmlStatus,
          layerId
        );
      })
      .catch(this.errorHandler);
  }
  get f() {
    return this.form.controls;
  }
  fillSelects() {
    this.searchAnp();
    try {
      this.agreementService.agreementStateList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.agreementStateList = response.items;
        }
      });
      this.agreementService.agreementSourceList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.sourceList = response.items;
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
  addShapefileToMap(
    featureCollection,
    htmlStatus?: HTMLElement,
    layerId?: number
  ) {
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

      this.graphics.push(geometry);
      this.layerId = layerId;
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
  listAlliedCategory() {
    try {
      this.agreementService.alliedCategoryList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.alliedCategoryList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error(
        'Error al traer la lista de categoría de suscriptores',
        'error',
        { autoClose: true }
      );
    }
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
      restHect: [0],
      restdet: '',
      sectNom: '',
      sectHect: [0],
      territoryMod: '',
      finanApa: false,
      finanNum: [0],
      comTxt: '',
      genObj: '',
      finanMod: '',
      fondName: '',
      allied: true,
      sectDet: '',
      agreementState: this.fb.group({
        id: [0],
      }),
      anp: this.fb.group({
        id: [0],
      }),
      areaAmbitc: [{ value: 0, disabled: true }],
      source: this.fb.group({
        id: [0],
      }),
      ecosystemType: this.fb.group({
        id: [0],
      }),
    });
    this.alliedForm = this.fb.group({
      id: [0],
      alliedCategory: this.fb.group({
        id: [
          0,
          Validators.compose([
            Validators.required,
            Validators.pattern('[^0]+'),
          ]),
        ],
      }),
      conservationAgreement: this.fb.group({
        id: [0],
      }),
      name: ['', Validators.required],
      description: [''],
      state: [true],
      registrationDate: [''],
    });
    this.formCreateCommitments = this.fb.group({
      id: [0],
      description: ['', Validators.required],
      registrationDate: [''],
      state: [true],
      indicator: [''],
      active: [true],
      conservationAgreement: this.fb.group({
        id: [this.agreementId],
      }),
      allied: this.fb.group({
        id: [
          0,
          Validators.compose([
            Validators.required,
            Validators.pattern('[^0]+'),
          ]),
        ],
      }),
      actionLine: this.fb.group({
        id: [
          0,
          Validators.compose([
            Validators.required,
            Validators.pattern('[^0]+'),
          ]),
        ],
      }),
    });
    this.anpForm = this.fb.group({
      item: [JSON.stringify({ name: '', code: '' })],
      paginator: [
        JSON.stringify({
          offset: '1',
          limit: '10',
          sort: 'name',
          order: 'asc',
        }),
      ],
    });
  }

  insertAgreement() {
    try {
      this.submitted = true;
      this.disabled = true;
      console.log(this.form.value);

      if (this.form.invalid) {
        this.disabled = false;
        return;
      }
      this.agreementService
        .agreementInsert(JSON.stringify(this.form.value))
        .subscribe((response) => {
          console.log(response);
          if (response && response.success === true) {
            this.submitted = false;
            this.agreementExist = true;
            this.getWorkPlan(response.extra);
            this.searchAllied();
            this.agreementId = response.extra.toString();
            this.alertService.success(
              response.message,
              'Ok',
              { autoClose: true }
            );
            this.getDetail(response.extra);
          } else {
            this.alertService.error('error: ' + response.message, 'error', {
              autoClose: true,
            });
          }
          this.disabled = false;
        });
    } catch (error) {
      this.disabled = false;
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

    if (this.esriJsons.length === 0) {
      return;
    }
    const edits = {
      addFeatures: this.esriJsons,
    };

    this.featureLayer = new FeatureLayer({
      url: environment.conservationAgreements[this.layerId].url,
      outFields: ['*'],
      popupEnabled: true,
      id: 'featureTest',
    });
    let thiss = this;

    this.featureLayer
      .applyEdits(edits)
      .then(function (editsResult) {
        console.log(editsResult);
        if (editsResult.addFeatureResults.length > 0) {
          thiss.alertService.success(
            'Se subieron exitosamente los shapefiles',
            'Ok',
            { autoClose: true }
          );
        }
      })
      .catch(function (error) {
        thiss.alertService.error(
          '[ applyEdits ] FAILURE: ' +
            error.code +
            ' | ' +
            error.name +
            ' | ' +
            error.message,
          'Ok',
          { autoClose: true }
        );
        console.log('error = ', error);
      });
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
            numFamily: response.item.numFamily,
            benFamily: response.item.benFamily,
            areaAmbitc: response.item.areaAmbitc,
            producedArea: response.item.producedArea,
            detalleProduction: response.item.detalleProduction,
            agreementState: { id: response.item.agreementState.id || 0 },
            restHect: response.item.restHect,
            restdet: response.item.restdet,
            sectNom: response.item.sectNom,
            sectHect: response.item.sectHect,
            sectDet: response.item.sectDet,
            territoryMod: response.item.territoryMod,
            finanApa: response.item.finanApa,
            finanNum: response.item.finanNum,
            comTxt: response.item.comTxt,
            genObj: response.item.genObj,
            finanMod: response.item.finanMod,
            fondName: response.item.fondName,
            allied: response.item.allied,
            anp: { id: response.item.anp.id || 0 },
            source: { id: response.item.source.id || 0 },
            ecosystemType: { id: 0 },
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
  addFieldValue() {
    console.log(this.newAttribute);
    this.fieldArray.push(this.newAttribute);
    this.newAttribute = {};
  }
  deleteFieldValue(index) {
    this.fieldArray.splice(index, 1);
  }
  insertAlliedCategory() {
    this.disabled = true;
    this.submitted = true;

    if (this.alliedForm.invalid) {
      this.disabled = false;
      return;
    }
    if (this.agreementExist === false || this.agreementId === null) {
      this.alertService.warn('Guarde primero el acuerdo', 'Info', {
        autoClose: true,
      });
      return;
    }
    try {
      this.agreementService
        .alliedCategoryInsert(JSON.stringify(this.alliedForm.value))
        .subscribe((response) => {
          if (response && response.success === true) {
            this.alertService.success(
              'Se registro correctamenteel el suscriptor',
              'Ok',
              { autoClose: true }
            );
            this.searchAllied();
          } else {
            this.alertService.error('error: ' + response.message, 'error', {
              autoClose: true,
            });
          }
          this.submitted = false;
          this.disabled = false;
        });
    } catch (error) {
      this.submitted = false;
      this.disabled = false;
      this.alertService.error(
        'Error al insertar un nuevo suscriptor',
        'error',
        { autoClose: true }
      );
    }
  }
  searchAllied() {
    if (
      this.agreementId ||
      this.agreementId === '' ||
      this.agreementId === null
    ) {
      return;
    }
    try {
      this.agreementService
        .alliedSearch(parseInt(this.agreementId))
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.alliedList = response.items;
          }
        });
    } catch (error) {
      this.alertService.error('Error al traer los suscriptores', 'error', {
        autoClose: true,
      });
    }
  }
  onDeleteAllied(id) {
    console.log(id);
  }
  onContentCreateCommitmentsModal(content) {
    this.modalRef = this.modalService.open(content, {
      size: 'sm',
      backdrop: 'static',
      centered: true,
    });
    this.masterPlanSearch();
  }
  insertCommitments() {
    this.submitted = true;
    this.disabled = true;
    console.log(this.formCreateCommitments.value);
    if (this.formCreateCommitments.invalid) {
      console.log('invalid');
      this.disabled = false;
      return;
    }
    try {
      this.agreementService
        .commitmentsInsert(JSON.stringify(this.formCreateCommitments.value))
        .subscribe((response) => {
          if (response && response.success === true) {
            this.alertService.success(
              'Se registro correctamente el compromiso',
              'Ok',
              { autoClose: true }
            );

            this.modalRef.close();
          } else {
            this.alertService.error('error: ' + response.message, 'error', {
              autoClose: true,
            });
          }
          this.formCreateCommitmentsReset();
          this.submitted = false;
          this.disabled = false;
        });
    } catch (error) {
      this.submitted = false;
      this.disabled = false;
      this.alertService.error('Error al insertar los compromisos', 'error', {
        autoClose: true,
      });
    }
  }
  formCreateCommitmentsReset() {
    this.formCreateCommitments.setValue({
      id: 0,
      description: '',
      registrationDate: '',
      state: true,
      indicator: '',
      active: true,
      conservationAgreement: {
        id: this.agreementId,
      },
      allied: {
        id: 0,
      },
      actionLine: {
        id: 0,
      },
    });
  }
  masterPlanSearch() {
    const id = this.form.get('anp').value.id;

    if (!id || id == 0) {
      return;
    }

    try {
      this.masterPlanService.masterPlanDetailByAnp(id).subscribe((response) => {
        if (response && response.item !== null && response.item !== {}) {
          this.searchGoals(response.item.id);
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer el plan maestro', 'Error', {
        autoClose: true,
      });
    }
  }
  searchGoals(id) {
    try {
      this.masterPlanService
        .masterPlanObjetiveList(id)
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.objectiveList = response.items;
          }
        });
    } catch (error) {
      this.alertService.error(
        'Error al traer la lista de objectivos',
        'Error',
        {
          autoClose: true,
        }
      );
    }
  }
  searchAnp() {
    try {
      this.anpService.anpSearch(this.anpForm.value).subscribe((response) => {
        if (response && response.items.length > 0) {
          this.anpList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer la lista de anp', 'Error', {
        autoClose: true,
      });
    }
  }
  searchActionLines(event) {
    const id = event.target.value;
    if (id == 0) {
      this.actionLineList = [];
      return;
    }
    try {
      this.masterPlanService.actionLineList(id).subscribe((response) => {
        if (response && response.items.length > 0) {
          this.actionLineList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer las lineas de acción', 'Error', {
        autoClose: true,
      });
    }
  }
}
