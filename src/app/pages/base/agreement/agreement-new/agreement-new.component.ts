import { Component, OnInit } from '@angular/core';
import { AgreementService } from '@app/_services/base/agreement.service';
import { MasterPlanService } from '@app/_services/masterplan/masterplan/master-plan.service';
import { AnpService } from '@app/_services/masterplan/anp/anp.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, zipWith } from 'rxjs';
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
import { ExcelService } from '@app/_services/report/excel.service';

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
  departments: any[] = [];
  provinces: any[] = [];
  districts: any[] = [];
  submitted: boolean = false;
  disabled: boolean = false;
  attributes: any = {
    codigo: '',
    nombre: '',
    areatotal: 0,
    comunidad: '',
    anexo: '',
    productor: '',
    type: 0,
  };
  graphics: any[] = [];
  layersGraphic: any[] = [
    {
      attributes: {
        codigo: '',
        nombre: environment.conservationAgreements[0].name,
        areatotal: 0,
        comunidad: '',
        anexo: '',
        url: environment.conservationAgreements[0].url,
        sended: false,
        id: 'ambito',
      },
      geometry: {},
    },
    {
      attributes: {
        codigo: '',
        nombre: environment.conservationAgreements[1].name,
        areatotal: 0,
        comunidad: '',
        anexo: '',
        url: environment.conservationAgreements[1].url,
        sended: false,
        id: 'vigilancia',
      },
      geometry: {},
    },
    {
      attributes: {
        codigo: '',
        nombre: environment.conservationAgreements[2].name,
        areatotal: 0,
        productor: '',
        url: environment.conservationAgreements[2].url,
        sended: false,
        id: 'restauracion',
      },
      geometry: {},
    },
  ];
  esriJsons: Graphic[] = [];

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
  alliedId: number = 0;
  commitmentsList: any[] = [];
  hasTotal: boolean = false;
  hasVigilance: boolean = false;
  hasRestauration: boolean = false;
  commitmentId: number = 0;
  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder,
    private alertService: AlertService,
    private route: ActivatedRoute,
    private router: Router,
    private modalService: NgbModal,
    private masterPlanService: MasterPlanService,
    private anpService: AnpService,
    private excelService: ExcelService
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
    if (
      this.route.snapshot.paramMap.get('id') !== undefined &&
      this.route.snapshot.paramMap.get('id') !== null &&
      this.route.snapshot.paramMap.get('id') !== ''
    ) {
      this.agreementId = this.route.snapshot.paramMap.get('id');
      this.agreementExist = true;
      this.edit = true;
      this.getDetail(this.agreementId);
    }

    let item = {
      name: '',
    };
    this.obsQuery.next({
      item: JSON.stringify(item),
    });
    this.fillSelects();

    this.searchAllied();
    this.searchCommitments();
    this.autocalculatedField();
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
  onDeleteAlliedModal(content, id) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.alliedId = id;
  }
  onDeleteCommitmentsModal(content, id) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.commitmentId = id;
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
          this.attributes.type = 1;
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
          this.attributes.type = 2;
          this.generateFeatureCollection(fileName, htmlStatus, htmlForm, 1);
        } else {
          htmlStatus.innerHTML =
            '<p style="color:red">Add shapefile as .zip file</p>';
        }
      });
    document
      .getElementById('uploadForm3')
      .addEventListener('change', (event) => {
        const fileName = (event.target as HTMLFormElement).value.toLowerCase();
        const htmlStatus = document.getElementById('upload-status3');
        if (fileName.indexOf('.zip') !== -1) {
          //is file a zip - if not notify user

          const htmlForm = document.getElementById(
            'uploadForm3'
          ) as HTMLFormElement;

          this.generateFeatureCollection(fileName, htmlStatus, htmlForm, 2);
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
    const fileForm3 = document.getElementById('mainWindow3');
    const expand3 = new Expand({
      expandIconClass: 'esri-icon-pan2',
      view: this.view,
      content: fileForm3,
      expandTooltip: 'área de restauración',
      group: 'fileform',
      id: 'fileform3',
    });

    this.view.ui.add(expand, 'top-right');
    this.view.ui.add(expand2, 'top-right');
    this.view.ui.add(expand3, 'top-right');
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
      this.searchDepartments();
    } catch (error) {
      this.alertService.error(
        'Error al traer la lista de estados del acuerdo',
        'error',
        { autoClose: true }
      );
    }
  }
  searchDepartments() {
    try {
      this.agreementService.departmentList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          this.departments = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer los departamentos', 'Error', {
        autoClose: true,
      });
    }
  }
  searchProvinces(event) {
    const id = event;
    if (id == 0) {
      this.provinces = [];
      this.districts = [];
      return;
    }

    try {
      this.agreementService
        .searchProvinces(id.toString())
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.provinces = response.items;
          }
        });
    } catch (error) {
      this.alertService.error('Error al traer las provincias', 'Error', {
        autoClose: true,
      });
    }
  }
  searchDistricts(event) {
    const id = event;
    if (id == 0) {
      this.districts = [];
      return;
    }
    try {
      this.agreementService.searchDistricts(id).subscribe((response) => {
        if (response && response.items.length > 0) {
          this.districts = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer las lineas de acción', 'Error', {
        autoClose: true,
      });
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

      // this.graphics.push(geometry);
      this.layersGraphic[layerId].geometry = geometry;
      this.layerId = layerId;
      this.layersGraphic[layerId].attributes.areatotal =
        this.form.get('areaAmbitc').value;
      this.layersGraphic[layerId].attributes.codigo =
        this.form.get('code').value;
      this.layersGraphic[layerId].attributes.nombre =
        this.form.get('name').value;
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
    console.log(error);
    // this.alertService.error(
    //   'Error al subir shapefile :' + error.message,
    //   'Error',
    //   { autoClose: true }
    // );
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
      code: [{ value: '', disabled: true }],
      vigency: [
        0,
        Validators.compose([Validators.required, Validators.min(1)]),
      ],
      firm: [''],
      partMen: [0],
      partWomen: [0],
      benPerson: [''],
      benIndirect: [''],
      numFamily: [0],
      benFamily: [''],
      localization: [''],
      producedArea: [0],
      detailProduction: [''],
      restHect: [0],
      restdet: [''],
      sectNom: [''],
      sectHect: [0],
      territoryMod: [''],
      finanApa: [false],
      finanNum: [0],
      comment: [''],
      genObj: [''],
      finanMod: [''],
      fondName: [''],
      allied: true,
      sectDet: [''],
      agreementState: this.fb.group({
        id: [0, Validators.min(1)],
      }),
      anp: this.fb.group({
        id: [0, Validators.min(1)],
      }),
      areaAmbitc: [{ value: 0, disabled: true }],
      source: this.fb.group({
        id: [0],
      }),
      ecosystemType: this.fb.group({
        id: [0],
      }),
      districtId: [''],
      department: [0, Validators.min(1)],
      province: [0, Validators.min(1)],
      district: [0, Validators.min(1)],
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
        id: [this.agreementId],
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
      // this.disabled = true;

      if (this.form.invalid) {
        this.disabled = false;

        return;
      }
      this.form.patchValue({
        districtId: this.form.get('district').value,
      });

      this.agreementService
        .agreementInsert(JSON.stringify(this.form.value))
        .subscribe((response) => {
          console.log(response);
          if (response && response.success === true) {
            this.agreementExist = true;

            this.getWorkPlan(response.extra);
            this.searchAllied();
            this.agreementId = response.extra.toString();
            // this.getDetail(response.extra);
            this.router.navigateByUrl(`/agreement/detail/${response.extra}`);
            this.alertService.success(response.message, 'Ok', {
              autoClose: true,
            });
          } else {
            this.alertService.error('error: ' + response.message, 'error', {
              autoClose: true,
            });
          }
          this.submitted = false;
          this.disabled = false;
        });
    } catch (error) {
      this.disabled = false;
      this.submitted = false;

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
      this.alertService.info('Agregue shapefiles', 'Ok', { autoClose: true });
      return;
    }
    // const edits = {
    //   addFeatures: this.esriJsons,
    // };
    console.log(this.esriJsons);
    // this.featureLayer = new FeatureLayer({
    //   url: environment.conservationAgreements[this.layerId].url,
    //   outFields: ['*'],
    //   popupEnabled: true,
    //   id: 'featureTest',
    // });

    this.disabled = true;

    this.esriJsons.forEach((graphic, index) => {
      const edits = {
        addFeatures: [graphic],
      };
      const featureLayer = new FeatureLayer({
        url: graphic.attributes.url,
        outFields: ['*'],
        popupEnabled: true,
        id: 'featureLayer',
      });

      featureLayer
        .applyEdits(edits)
        .then((editsResult) => {
          console.log(editsResult);
          if (editsResult.addFeatureResults.length > 0) {
            this.layersGraphic[index].attributes.sended = true;
            if (index === 2) {
              this.disabled = false;
            }
          }
        })
        .catch((error) => {
          this.disabled = false;
          console.log(error);
        });
    });
    // this.featureLayer
    //   .applyEdits(edits)
    //   .then(function (editsResult) {
    //     console.log(editsResult);
    //     if (editsResult.addFeatureResults.length > 0) {
    //       // thiss.blockExpand();
    //       thiss.alertService.success(
    //         'Se subieron exitosamente los shapefiles',
    //         'Ok',
    //         { autoClose: true }
    //       );
    //     }
    //     thiss.disabled = false;
    //   })
    //   .catch(function (error) {
    //     thiss.disabled = false;
    //     thiss.alertService.error(
    //       '[ applyEdits ] FAILURE: ' +
    //         error.code +
    //         ' | ' +
    //         error.name +
    //         ' | ' +
    //         error.message,
    //       'Ok',
    //       { autoClose: true }
    //     );
    //     console.log('error = ', error);
    //   });
  }

  buildEsriJson() {
    // codigo: '',
    // nombre: '',
    // areatotal: 0,
    // comunidad: '',
    // anexo: '',
    // productor: '',

    this.layersGraphic.forEach((layer) => {
      if (layer.geometry !== {}) {
        this.esriJsons.push(
          Graphic.fromJSON({
            attributes: layer.attributes,
            geometry: layer.geometry[0],
          })
        );
      }
    });

    // for (let i = 0; i < 4; i++) {
    //   if (this.graphics[i] === undefined || this.graphics[i] === null) {
    //     continue;
    //   }
    //   console.log(this.graphics[i][0]);
    //   this.esriJsons.push(
    //     Graphic.fromJSON({
    //       attributes: this.attributes,
    //       geometry: this.graphics[i][0],
    //     })
    //   );
    // }
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
            detailProduction: response.item.detailProduction,
            agreementState: { id: response.item.agreementState.id || 0 },
            restHect: response.item.restHect,
            restdet: response.item.restdet,
            sectNom: response.item.sectNom,
            sectHect: response.item.sectHect,
            sectDet: response.item.sectDet,
            territoryMod: response.item.territoryMod,
            finanApa: response.item.finanApa,
            finanNum: response.item.finanNum,
            comment: response.item.comment,
            genObj: response.item.genObj,
            finanMod: response.item.finanMod,
            fondName: response.item.fondName,
            allied: response.item.allied,
            anp: { id: response.item.anp.id || 0 },
            source: { id: response.item.source.id || 0 },
            ecosystemType: { id: 0 },
            localization: '',
            districtId: response.item.districtId,
            department: 0,
            province: 0,
            district: 0,
          });
          this.addAgreementLayers();
          this.setLocalization(response.item.districtId);
          this.disableFields();
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
  disableFields() {
    this.form.get('department').disable();
    this.form.get('province').disable();
    this.form.get('district').disable();
    this.form.get('code').disable();
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
    if (this.edit === false) {
      if (this.agreementExist === false) {
        this.alertService.warn('Guarde primero el acuerdo', 'Info', {
          autoClose: true,
        });
        return;
      }
    } else {
      if (
        this.agreementId === '' ||
        this.agreementId === null ||
        this.agreementId === '0'
      ) {
        return;
      }
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
            this.forAlliedReset();

            this.searchAllied();
            this.modalRef.close();
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
      this.agreementId === '0' ||
      this.agreementId === '' ||
      this.agreementId === null
    ) {
      console.log(this.agreementId);
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
  onDeleteAllied() {
    try {
      this.agreementService
        .alliedDelete(this.alliedId)
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(response.message, 'Ok', {
              autoClose: true,
            });

            this.searchAllied();
          }
          this.modalRef.close();
        });
    } catch (error) {
      this.alertService.error('Error al eliminar el suscriptor', 'Error', {
        autoClose: true,
      });
    }
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

    if (this.formCreateCommitments.invalid) {
      this.disabled = false;
      return;
    }
    if (this.edit === false) {
      if (this.agreementExist === false) {
        this.disabled = false;
        this.alertService.warn('Guarde primero el acuerdo', 'Info', {
          autoClose: true,
        });
        return;
      }
    } else {
      if (
        this.agreementId === '' ||
        this.agreementId === null ||
        this.agreementId === '0'
      ) {
        this.disabled = false;
        return;
      }
    }
    this.formCreateCommitments.patchValue({
      conservationAgreement: { id: this.agreementId },
    });
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
            this.searchCommitments();
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
  searchCommitments() {
    if (
      this.agreementId === '' ||
      this.agreementId === null ||
      this.agreementId === '0'
    ) {
      return;
    }
    try {
      this.agreementService
        .commitmentsSearch(this.agreementId)
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.commitmentsList = response.items;
          }
        });
    } catch (error) {
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
      this.anpService.anpList().subscribe((response) => {
        if (response && response.items.length > 0) {
          this.anpList = response.items;
        }
      });
      //this.anpService.anpSearch(this.anpForm.value).subscribe((response) => {
      //  if (response && response.items.length > 0) {
      //    this.anpList = response.items;
      //  }
      //});
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
  forAlliedReset() {
    this.alliedForm.setValue({
      id: 0,
      alliedCategory: {
        id: 0,
      },
      conservationAgreement: {
        id: this.agreementId,
      },
      name: '',
      description: '',
      state: true,
      registrationDate: '',
    });
  }
  autocalculatedField() {
    this.form.valueChanges.subscribe((val) => {
      const valueA =
        parseFloat(val.producedArea) > 0 ? parseFloat(val.producedArea) : 0;
      const valueB =
        parseFloat(val.restHect) > 0 ? parseFloat(val.restHect) : 0;
      const valueC =
        parseFloat(val.sectHect) > 0 ? parseFloat(val.sectHect) : 0;

      const newVal = valueA + valueB + valueC;
      this.form.controls.areaAmbitc.patchValue(newVal.toFixed(5), {
        emitEvent: false,
      });
      this.validateForm();
    });
  }
  validateForm() {
    Object.keys(this.form.controls).forEach((key) => {
      this.form.controls[key].markAsTouched();
      this.form.controls[key].updateValueAndValidity({ emitEvent: false });
    });
  }
  blockExpand() {
    if (this.attributes.type === 1) {
      this.hasTotal === true;
    } else if (this.attributes.type === 2) {
      this.hasVigilance === true;
    } else if (this.attributes.type === 3) {
      this.hasRestauration === true;
    }
  }
  addAgreementLayers() {
    const agreementCode = this.form.get('code').value;
    console.log(agreementCode);
    if (
      agreementCode === null ||
      agreementCode === undefined ||
      agreementCode === ''
    ) {
      return;
    }
    this.layersGraphic.forEach((layer, index) => {
      const featureLayer = new FeatureLayer({
        url: layer.attributes.url,
        id: layer.attributes.id,
        popupEnabled: true,
        outFields: ['*'],
        definitionExpression: `codigo = '${agreementCode}'`,
      });
      // featureLayer.when((loaded) => {
      //   console.log(loaded);
      // });
      this.map.add(featureLayer);
      if (index == 1) {
        this.zoomToLayer(featureLayer);
      }
      // this.addGraphics(featureLayer);
    });
    // this.zoomToLayer;
  }
  zoomToLayer(layer: FeatureLayer) {
    layer
      .queryExtent()
      .then((response) => {
        this.view.goTo(response.extent);
      })
      .catch((error) => {
        this.alertService.error('Error al traer el extent' + error, 'error', {
          autoClose: false,
        });
      });
  }
  onDeleteCommitment() {
    try {
      this.agreementService
        .commitmentDelete(this.commitmentId)
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(
              'Se elimino correctamente el compromiso',
              'Ok',
              {
                autoClose: false,
              }
            );
            this.commitmentsList = [];
            this.searchCommitments();
            this.modalRef.close();
          }
        });
    } catch (error) {
      this.alertService.error(
        'Error al eliminar el compromiso' + error,
        'error',
        {
          autoClose: false,
        }
      );
    }
  }
  setLocalization(districId: string) {
    if (districId.length < 4) {
      return;
    }
    this.searchProvinces(districId.substring(0, 2));
    this.searchDistricts(districId.substring(0, 4));
    this.form.patchValue({
      department: districId.substring(0, 2),
      province: districId.substring(0, 4),
      district: districId,
    });
  }
  exportAsXLSX() {
    if (this.commitmentsList.length === 0) {
      return;
    }
    this.excelService.exportAsExcelFile(this.commitmentsList, 'sample');
  }
}
