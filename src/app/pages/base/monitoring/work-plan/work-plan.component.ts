import { Component, OnInit, OnDestroy } from '@angular/core';
import { MonitoringService } from '../../../../_services/base/monitoring.service';
import { AlertService } from '../../../../_services/base/alert.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { AgreementService } from '@app/_services/base/agreement.service';
import { Router, ActivatedRoute } from '@angular/router';
import { AnpService } from '@app/_services/masterplan/anp/anp.service';
import { WorkPlanService } from '@app/_services/base/work-plan.service';

import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { query } from '@angular/animations';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { debug } from 'console';
@Component({
  selector: 'app-work-plan',
  templateUrl: './work-plan.component.html',
  styleUrls: ['./work-plan.component.css'],
})
export class WorkPlanComponent implements OnInit, OnDestroy {
  agreementList: any[];
  total: number;
  isLoading: boolean = false;
  pageSize: any;
  page: Number;
  form: FormGroup;
  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });
  parsed: any;
  commitmentsList: any[] = [];
  agreementId: string = '';
  modalRef: NgbModalRef;
  fieldArrayList: any[] = [];
  fieldArray: Array<any> = [];
  fieldArrayTotalTemp: any[] = [];
  fieldArrayMonitoringList: any[] = [];

  newAttribute: any = {};
  editAttribute: any = {};
  anpForm: FormGroup;
  anpList: any[] = [];
  agreementStateList: any[] = [];
  vigency: number = 0;
  departments: any[] = [];
  provinces: any[] = [];
  districts: any[] = [];
  workPlanForm: FormGroup;
  formActivity: FormGroup;

  commitmentId: number = 0;
  disabled: boolean = false;
  monitoringList: any[] = [];
  monitoringListHistory: any[] = [];
  monitoringHistoryCount: number = 0;
  formMonitoring: FormGroup;
  submitted: boolean = false;
  monitoringReady: boolean = false;

  constructor(
    private monitoringService: MonitoringService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder,
    private agreementService: AgreementService,
    private route: ActivatedRoute,
    private modalService: NgbModal,
    private anpService: AnpService,
    private workPlanService: WorkPlanService
  ) {}

  ngOnInit(): void {
    this.buildForm();
    if (
      this.route.snapshot.paramMap.get('id') !== undefined &&
      this.route.snapshot.paramMap.get('id') !== null &&
      this.route.snapshot.paramMap.get('id') !== ''
    ) {
      this.agreementId = this.route.snapshot.paramMap.get('id');
      this.searchAnp();
      this.searchDepartments();
      this.searchAgreementState();
      this.getDetail(this.agreementId);
      this.searchMonitoring();
      this.monitoringSearchHistory();
    }

    this.searchCommitments();

    // this.onSearch();
  }
  get f() {
    return this.formMonitoring.controls;
  }
  onDeleteModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
  }
  deleteMonitoring() {}
  getPage(page: number) {
    this.parseData('paginator', 'offset', page);
    // this.onSearch();
  }
  parseData(parent, key, value) {
    // debugger;
    const item = this.queryObserver.getValue();

    if (typeof item[parent] === 'string') {
      let parsed = JSON.parse(item[parent]);
      parsed[key] = value;
      item[parent] = JSON.stringify(parsed);
      this.queryObserver.next(item);
    }
  }
  search(filters: any): void {
    // const q = this.queryObserver.getValue();
    // q.item = JSON.stringify(filters);
    // this.queryObserver.next(q);
    // console.log(this.queryObserver.getValue());
    // this.onSearch();
  }
  onChangePageSize(event) {
    // const q = this.queryObserver.getValue();
    // q.paginator['limit'] = this.f.pageSizes.value;
    // this.parseData('paginator', 'limit', parseInt(this.f.pageSize.value));
    // this.onSearch();
    // this.queryObserver.next({item:this.f.})
  }

  buildForm(): void {
    this.form = this.fb.group({
      name: [{ value: '', disabled: true }],
      anp: this.fb.group({
        id: [{ value: '', disabled: true }],
      }),

      code: [{ value: '', disabled: true }],
      firm: [{ value: '', disabled: true }],
      vigency: [{ value: '', disabled: true }],
      objective: [{ value: '', disabled: true }],

      agreementState: this.fb.group({
        id: [{ value: '', disabled: true }],
      }),
      districtId: [''],
      department: [{ value: 0, disabled: true }],
      province: [{ value: 0, disabled: true }],
      district: [{ value: 0, disabled: true }],
      localization: [{ value: '', disabled: true }],
      description: [{ value: '', disabled: true }],
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
    this.workPlanForm = this.fb.group({
      conservationAgreement: this.fb.group({
        id: 0,
      }),
      year: '',
      activities: new FormArray([]),
    });
    this.formActivity = this.fb.group({
      id: 0,
      activity: '',
      indicator: '',
      goal: 0,
      semester: [],
      sem1: false,
      sem2: false,
    });
    this.formMonitoring = this.fb.group({
      comment: ['', Validators.required],
      recommendation: ['', Validators.required],
      evaluation: ['', Validators.required],
      description: [''],
      achievement: ['', Validators.required],
      activities: new FormArray([]),
    });
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
          console.log(response);
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
          this.vigency = response.item.vigency;
          this.form.setValue({
            name: response.item.name,
            code: response.item.code,
            vigency: response.item.vigency,
            firm: response.item.firm,
            agreementState: { id: response.item.agreementState.id || 0 },
            objective: '',
            anp: { id: response.item.anp.id || 0 },
            localization: response.item.localization || 'Sin datos',
            districtId: response.item.districtId,
            department: 0,
            province: 0,
            district: 0,
            description: response.item.description,
          });
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
    // this.form.get('department').();
    // this.form.get('province').disable();
    // this.form.get('district').disable();
    // this.form.get('code').disable();
  }
  onCreateActivityModal(content, id) {
    this.modalRef = this.modalService.open(content, {
      size: 'lg',
      backdrop: 'static',
      centered: true,
    });
    console.log(this.fieldArrayTotalTemp);
    this.commitmentId = id;

    this.activityListByCommitment();
  }
  onCreateEvaluationModal(content) {
    this.modalRef = this.modalService.open(content, {
      size: 'sm',
      backdrop: 'static',
      centered: true,
    });
  }
  addFieldValue() {
    console.log(this.newAttribute);
    //prettier-ignore
    if (
      (!this.existKey('goal', this.newAttribute) || this.newAttribute.goal === 0)||
        (!this.existKey('name', this.newAttribute) || this.newAttribute.name === "")||
        (!this.existKey('indicator', this.newAttribute) || this.newAttribute.indicator === "")||
        ((!this.existKey('trim1', this.newAttribute) || this.newAttribute.trim1 === false)&&(!this.existKey('trim2', this.newAttribute) || this.newAttribute.trim2 === false) )
         
    ) {
      this.alertService.error('Complete los campos requeridos', 'Error', {
        autoClose: true,
      });
      return;
    }

    this.fieldArray.push(this.newAttribute);

    this.newAttribute = {};
  }
  existKey(key: string, obj: Object) {
    return obj.hasOwnProperty(key);
  }
  deleteFieldValue(index) {
    this.fieldArray.splice(index, 1);
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
  searchAgreementState() {
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
  saveWorkPlan() {
    this.buildItem();
    this.disabled = true;
    console.log(this.workPlanForm.value);

    try {
      this.workPlanService
        .workPlanInsert(JSON.stringify(this.workPlanForm.value))
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(
              'Se guardaron correctamente los datos del plan de trabajo',
              'Ok',
              {
                autoClose: true,
              }
            );
            this.searchMonitoring();
            this.monitoringSearchHistory();
            this.fieldArrayTotalTemp = [];
          } else {
            this.alertService.error('' + response.message, 'Error', {
              autoClose: true,
            });
          }
          this.disabled = false;
        });
    } catch (error) {
      this.disabled = false;
      this.alertService.error('Error al guardar el plan de trabajo', 'Error', {
        autoClose: true,
      });
    }

    // console.log(this.workPlanForm.value);
  }
  buildItem() {
    const newFieldArray: any[] = this.fieldArrayTotalTemp.map((obj) => {
      const o = Object.assign({}, obj);
      const temArray = [];
      o.semester = '';
      if (o.hasOwnProperty('trim1')) {
        if (o.trim1) {
          temArray.push('1');
        }
      }

      if (o.hasOwnProperty('trim2')) {
        if (o.trim2) {
          temArray.push('2');
        }
      }
      o.semester = temArray.join(',');

      return o;
    });
    let langArr = <FormArray>this.workPlanForm.get('activities');
    langArr.clear();
    newFieldArray.forEach((item, index) => {
      langArr.push(this.fb.group(item));
    });

    this.workPlanForm.patchValue({
      conservationAgreement: { id: this.agreementId },
    });
    console.log(this.workPlanForm.value);
  }
  activityListByCommitment() {
    if (
      this.commitmentId === 0 ||
      this.commitmentId === undefined ||
      this.commitmentId === null
    ) {
      return;
    }

    // const activitiesFound = this.fieldArrayTotalTemp.find(
    //   (x) => x.commitmentId == this.commitmentId
    // );
    // console.log(activitiesFound);
    // if (activitiesFound === undefined || activitiesFound === null) {
    // try {
    //   this.workPlanService
    //     .activityListByCommitment(this.commitmentId)
    //     .subscribe((response) => {
    //       if (response && response.items.length > 0) {
    //         console.log(response);
    //         this.fieldArray = this.setCheckBoxes(response.items);
    //       } else {
    //         this.fieldArray = [];
    //       }
    //     });
    // } catch (error) {
    //   this.alertService.error(
    //     'Error al traer la lista de actividades',
    //     'Error',
    //     { autoClose: true }
    //   );
    // }
    // } else {

    this.fieldArray = this.fieldArrayTotalTemp.filter(
      (x) => x.commitmentId === this.commitmentId
    );
    // }
  }
  editFieldValue(field) {
    field.edit = true;
  }
  saveFieldValue(field, index) {
    this.fieldArray.splice(index, 1);
    this.fieldArray.push(field);
    field.edit = false;
  }
  setCheckBoxes(items: any[]) {
    const parsed = items.map((item) => {
      if (item.semester !== null && item.semester !== '') {
        let arr: any[] = item.semester.split(',');
        let found1 = arr.find((x) => x === '1');
        if (found1) {
          item.trim1 = true;
        }
        let found2 = arr.find((x) => x === '2');
        if (found2) {
          item.trim2 = true;
        }
      }
      return item;
    });

    return parsed;
  }

  saveActivityTemp() {
    this.fieldArrayTotalTemp = this.fieldArrayTotalTemp.filter((obj) => {
      return obj.commitmentId !== this.commitmentId;
    });
    console.log(this.fieldArray);
    console.log(this.fieldArrayTotalTemp);

    this.fieldArray.map((activity) => {
      activity.commitment = {
        id: this.commitmentId,
        actionLine: { objetive: { description: '' } },
        progress: 0,
      };
      activity.commitmentId = this.commitmentId;
      if (
        activity.id === undefined ||
        activity.id === null ||
        activity.id === ''
      ) {
        activity.isNew = true;
      }

      this.fieldArrayTotalTemp.push(activity);
    });
    this.modalRef.close();
  }
  cleanTempField() {
    this.searchMonitoring();
  }
  searchMonitoring() {
    if (
      this.agreementId === undefined ||
      this.agreementId === null ||
      this.agreementId === ''
    ) {
      return;
    }
    try {
      this.workPlanService
        .monitoringSearch(this.agreementId)
        .subscribe((response) => {
          console.log(response);
          if (
            response &&
            response.item !== null &&
            response.item.activities.length > 0
          ) {
            
            let reponseFake = {
              id: 0,
              conservationAgreement: null,
              name: null,
              description: null,
              registrationDate: '2021-10-17T22:11:49.228-0500',
              state: false,
              year: 0,
              version: 0,
              active: null,
              activities: [
                {
                  id: 1,
                  commitment: {
                    id: 1,
                    allied: {
                      id: 2,
                      conservationAgreement: {
                        id: 192,
                        source: null,
                        anp: {
                          id: 1,
                          name: 'Bosques Nublados de Udima',
                          withMasterPlan: 1,
                          fullName:
                            'Refugio de Vida Silvestre Bosques Nublados de Udima',
                          category: 'Refugio de Vida Silvestre',
                          district: 'Lima',
                          code: 'C01',
                        },
                        districtId: '170101',
                        name: 'Acuerdo AC',
                        description: 'a',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        vigency: 1,
                        code: 'AC2021170001',
                        firm: '2021-10-11',
                        benPerson: '',
                        detailProduction: '',
                        restdet: '',
                        comment: '',
                        allied: false,
                        localization: 'a',
                        surfaceAmbito: 0,
                        surfaceIntervention: '',
                        hasMasterPlan: true,
                        hasDevelopmentPlan: true,
                        livePlan: 0,
                        institutionalPlan: 0,
                        forestZoning: 0,
                        detailMunicipality: '',
                        hasFirm: true,
                        hasWorkPlan: true,
                        hasActas: true,
                        hasMap: true,
                        hasShape: true,
                        hasMonitoring: true,
                        agreementState: {
                          id: 5,
                          name: 'Evaluación',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        sectDet: '',
                        benIndirect: '',
                        producedArea: 0,
                        finanNum: 0,
                        areaAmbitc: 0,
                        partWomen: 0,
                        numFamily: 0,
                        partMen: 0,
                        territoryMod: '',
                        benFamily: '',
                        genObj: 'a',
                        fondName: '',
                        sectNom: '',
                        restHect: 0,
                        finanMod: '',
                        sectHect: 0,
                        finanApa: false,
                      },
                      alliedCategory: {
                        id: 13,
                        name: 'Jefatura',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      name: 'sernanp',
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                    },
                    actionLine: {
                      id: 1,
                      name: 'Seguridad',
                      rowspan: 2,
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      objetive: {
                        id: 1,
                        masterPlan: {
                          id: 1,
                          name: 'Plan de Trabajo Nº 1',
                          description: '2015-2020',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                          version: 1,
                          active: true,
                          anp: {
                            id: 1,
                            name: 'Bosques Nublados de Udima',
                            withMasterPlan: 1,
                            fullName:
                              'Refugio de Vida Silvestre Bosques Nublados de Udima',
                            category: 'Refugio de Vida Silvestre',
                            district: 'Lima',
                            code: 'C01',
                          },
                        },
                        component: {
                          id: 6,
                          name: 'Ambiental',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        description: 'Seguridad en el campo',
                        rowspan: 2,
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        code: 'OB1',
                      },
                    },
                    description: 'vvvvvvvvvvvvvvvv',
                    rowspan: 1,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    indicator: '',
                    active: true,
                    progress: 0,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  workPlan: {
                    id: 1,
                    name: 'Plan de Trabajo N° 1',
                    description: null,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    year: 0,
                    version: 1,
                    active: true,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  name: 'aa',
                  description: null,
                  registrationDate: '2021-10-17T22:11:49.228-0500',
                  state: true,
                  goal: 100,
                  active: null,
                  indicator: 'a',
                  semester: '1',
                  progress: 0,
                  trim1: true,
                  commitmentId: 1,
                  //edit: false,
                  //rowSpan: 2,
                  //found: false,
                },
                {
                  id: 2,
                  commitment: {
                    id: 2,
                    allied: {
                      id: 3,
                      conservationAgreement: {
                        id: 192,
                        source: null,
                        anp: {
                          id: 1,
                          name: 'Bosques Nublados de Udima',
                          withMasterPlan: 1,
                          fullName:
                            'Refugio de Vida Silvestre Bosques Nublados de Udima',
                          category: 'Refugio de Vida Silvestre',
                          district: 'Lima',
                          code: 'C01',
                        },
                        districtId: '170101',
                        name: 'Acuerdo AC',
                        description: 'a',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        vigency: 1,
                        code: 'AC2021170001',
                        firm: '2021-10-11',
                        benPerson: '',
                        detailProduction: '',
                        restdet: '',
                        comment: '',
                        allied: false,
                        localization: 'a',
                        surfaceAmbito: 0,
                        surfaceIntervention: '',
                        hasMasterPlan: true,
                        hasDevelopmentPlan: true,
                        livePlan: 0,
                        institutionalPlan: 0,
                        forestZoning: 0,
                        detailMunicipality: '',
                        hasFirm: true,
                        hasWorkPlan: true,
                        hasActas: true,
                        hasMap: true,
                        hasShape: true,
                        hasMonitoring: true,
                        agreementState: {
                          id: 5,
                          name: 'Evaluación',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        sectDet: '',
                        benIndirect: '',
                        producedArea: 0,
                        finanNum: 0,
                        areaAmbitc: 0,
                        partWomen: 0,
                        numFamily: 0,
                        partMen: 0,
                        territoryMod: '',
                        benFamily: '',
                        genObj: 'a',
                        fondName: '',
                        sectNom: '',
                        restHect: 0,
                        finanMod: '',
                        sectHect: 0,
                        finanApa: false,
                      },
                      alliedCategory: {
                        id: 11,
                        name: 'Empresas',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      name: 'megantoli',
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                    },
                    actionLine: {
                      id: 1,
                      name: 'Seguridad',
                      rowspan: 0,
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      objetive: {
                        id: 1,
                        masterPlan: {
                          id: 1,
                          name: 'Plan de Trabajo Nº 1',
                          description: '2015-2020',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                          version: 1,
                          active: true,
                          anp: {
                            id: 1,
                            name: 'Bosques Nublados de Udima',
                            withMasterPlan: 1,
                            fullName:
                              'Refugio de Vida Silvestre Bosques Nublados de Udima',
                            category: 'Refugio de Vida Silvestre',
                            district: 'Lima',
                            code: 'C01',
                          },
                        },
                        component: {
                          id: 6,
                          name: 'Ambiental',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        description: 'Seguridad en el campo',
                        rowspan: 0,
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        code: 'OB1',
                      },
                    },
                    description: 'aaaaaaaaaaaaaa',
                    rowspan: 1,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    indicator: '',
                    active: true,
                    progress: 0,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  workPlan: {
                    id: 1,
                    name: 'Plan de Trabajo N° 1',
                    description: null,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    year: 0,
                    version: 1,
                    active: true,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  name: 'v',
                  description: null,
                  registrationDate: '2021-10-17T22:11:49.228-0500',
                  state: true,
                  goal: 100,
                  active: null,
                  indicator: 'v',
                  semester: '1',
                  progress: 0,
                  trim1: true,
                  commitmentId: 2,
                  edit: false,
                  rowSpan: 1,
                  found: false,
                },
                {
                  id: 3,
                  commitment: {
                    id: 3,
                    allied: {
                      id: 2,
                      conservationAgreement: {
                        id: 192,
                        source: null,
                        anp: {
                          id: 1,
                          name: 'Bosques Nublados de Udima',
                          withMasterPlan: 1,
                          fullName:
                            'Refugio de Vida Silvestre Bosques Nublados de Udima',
                          category: 'Refugio de Vida Silvestre',
                          district: 'Lima',
                          code: 'C01',
                        },
                        districtId: '170101',
                        name: 'Acuerdo AC',
                        description: 'a',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        vigency: 1,
                        code: 'AC2021170001',
                        firm: '2021-10-11',
                        benPerson: '',
                        detailProduction: '',
                        restdet: '',
                        comment: '',
                        allied: false,
                        localization: 'a',
                        surfaceAmbito: 0,
                        surfaceIntervention: '',
                        hasMasterPlan: true,
                        hasDevelopmentPlan: true,
                        livePlan: 0,
                        institutionalPlan: 0,
                        forestZoning: 0,
                        detailMunicipality: '',
                        hasFirm: true,
                        hasWorkPlan: true,
                        hasActas: true,
                        hasMap: true,
                        hasShape: true,
                        hasMonitoring: true,
                        agreementState: {
                          id: 5,
                          name: 'Evaluación',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        sectDet: '',
                        benIndirect: '',
                        producedArea: 0,
                        finanNum: 0,
                        areaAmbitc: 0,
                        partWomen: 0,
                        numFamily: 0,
                        partMen: 0,
                        territoryMod: '',
                        benFamily: '',
                        genObj: 'a',
                        fondName: '',
                        sectNom: '',
                        restHect: 0,
                        finanMod: '',
                        sectHect: 0,
                        finanApa: false,
                      },
                      alliedCategory: {
                        id: 13,
                        name: 'Jefatura',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      name: 'sernanp',
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                    },
                    actionLine: {
                      id: 22,
                      name: 'v',
                      rowspan: 2,
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      objetive: {
                        id: 2,
                        masterPlan: {
                          id: 1,
                          name: 'Plan de Trabajo Nº 1',
                          description: '2015-2020',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                          version: 1,
                          active: true,
                          anp: {
                            id: 1,
                            name: 'Bosques Nublados de Udima',
                            withMasterPlan: 1,
                            fullName:
                              'Refugio de Vida Silvestre Bosques Nublados de Udima',
                            category: 'Refugio de Vida Silvestre',
                            district: 'Lima',
                            code: 'C01',
                          },
                        },
                        component: {
                          id: 6,
                          name: 'Ambiental',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        description: 'vvvv',
                        rowspan: 2,
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        code: 'OB2',
                      },
                    },
                    description: 'vvvvvvvvvvv',
                    rowspan: 2,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    indicator: '',
                    active: true,
                    progress: 0,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  workPlan: {
                    id: 1,
                    name: 'Plan de Trabajo N° 1',
                    description: null,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    year: 0,
                    version: 1,
                    active: true,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  name: 'b',
                  description: null,
                  registrationDate: '2021-10-17T22:11:49.228-0500',
                  state: true,
                  goal: 100,
                  active: null,
                  indicator: 'b',
                  semester: '1',
                  progress: 0,
                  trim1: true,
                  commitmentId: 3,
                  edit: false,
                  rowSpan: 2,
                  found: false,
                },
                {
                  id: 4,
                  commitment: {
                    id: 3,
                    allied: {
                      id: 2,
                      conservationAgreement: {
                        id: 192,
                        source: null,
                        anp: {
                          id: 1,
                          name: 'Bosques Nublados de Udima',
                          withMasterPlan: 1,
                          fullName:
                            'Refugio de Vida Silvestre Bosques Nublados de Udima',
                          category: 'Refugio de Vida Silvestre',
                          district: 'Lima',
                          code: 'C01',
                        },
                        districtId: '170101',
                        name: 'Acuerdo AC',
                        description: 'a',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        vigency: 1,
                        code: 'AC2021170001',
                        firm: '2021-10-11',
                        benPerson: '',
                        detailProduction: '',
                        restdet: '',
                        comment: '',
                        allied: false,
                        localization: 'a',
                        surfaceAmbito: 0,
                        surfaceIntervention: '',
                        hasMasterPlan: true,
                        hasDevelopmentPlan: true,
                        livePlan: 0,
                        institutionalPlan: 0,
                        forestZoning: 0,
                        detailMunicipality: '',
                        hasFirm: true,
                        hasWorkPlan: true,
                        hasActas: true,
                        hasMap: true,
                        hasShape: true,
                        hasMonitoring: true,
                        agreementState: {
                          id: 5,
                          name: 'Evaluación',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        sectDet: '',
                        benIndirect: '',
                        producedArea: 0,
                        finanNum: 0,
                        areaAmbitc: 0,
                        partWomen: 0,
                        numFamily: 0,
                        partMen: 0,
                        territoryMod: '',
                        benFamily: '',
                        genObj: 'a',
                        fondName: '',
                        sectNom: '',
                        restHect: 0,
                        finanMod: '',
                        sectHect: 0,
                        finanApa: false,
                      },
                      alliedCategory: {
                        id: 13,
                        name: 'Jefatura',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      name: 'sernanp',
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                    },
                    actionLine: {
                      id: 22,
                      name: 'v',
                      rowspan: 0,
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      objetive: {
                        id: 2,
                        masterPlan: {
                          id: 1,
                          name: 'Plan de Trabajo Nº 1',
                          description: '2015-2020',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                          version: 1,
                          active: true,
                          anp: {
                            id: 1,
                            name: 'Bosques Nublados de Udima',
                            withMasterPlan: 1,
                            fullName:
                              'Refugio de Vida Silvestre Bosques Nublados de Udima',
                            category: 'Refugio de Vida Silvestre',
                            district: 'Lima',
                            code: 'C01',
                          },
                        },
                        component: {
                          id: 6,
                          name: 'Ambiental',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        description: 'vvvv',
                        rowspan: 0,
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        code: 'OB2',
                      },
                    },
                    description: 'vvvvvvvvvvv',
                    rowspan: 0,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    indicator: '',
                    active: true,
                    progress: 0,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  workPlan: {
                    id: 1,
                    name: 'Plan de Trabajo N° 1',
                    description: null,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    year: 0,
                    version: 1,
                    active: true,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  name: 'b',
                  description: null,
                  registrationDate: '2021-10-17T22:11:49.228-0500',
                  state: true,
                  goal: 100,
                  active: null,
                  indicator: 'b',
                  semester: '',
                  progress: 0,
                  commitmentId: 3,
                  edit: false,
                  rowSpan: 1,
                  found: false,
                },
                {
                  id: 5,
                  commitment: {
                    id: 4,
                    allied: {
                      id: 3,
                      conservationAgreement: {
                        id: 192,
                        source: null,
                        anp: {
                          id: 1,
                          name: 'Bosques Nublados de Udima',
                          withMasterPlan: 1,
                          fullName:
                            'Refugio de Vida Silvestre Bosques Nublados de Udima',
                          category: 'Refugio de Vida Silvestre',
                          district: 'Lima',
                          code: 'C01',
                        },
                        districtId: '170101',
                        name: 'Acuerdo AC',
                        description: 'a',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        vigency: 1,
                        code: 'AC2021170001',
                        firm: '2021-10-11',
                        benPerson: '',
                        detailProduction: '',
                        restdet: '',
                        comment: '',
                        allied: false,
                        localization: 'a',
                        surfaceAmbito: 0,
                        surfaceIntervention: '',
                        hasMasterPlan: true,
                        hasDevelopmentPlan: true,
                        livePlan: 0,
                        institutionalPlan: 0,
                        forestZoning: 0,
                        detailMunicipality: '',
                        hasFirm: true,
                        hasWorkPlan: true,
                        hasActas: true,
                        hasMap: true,
                        hasShape: true,
                        hasMonitoring: true,
                        agreementState: {
                          id: 5,
                          name: 'Evaluación',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        sectDet: '',
                        benIndirect: '',
                        producedArea: 0,
                        finanNum: 0,
                        areaAmbitc: 0,
                        partWomen: 0,
                        numFamily: 0,
                        partMen: 0,
                        territoryMod: '',
                        benFamily: '',
                        genObj: 'a',
                        fondName: '',
                        sectNom: '',
                        restHect: 0,
                        finanMod: '',
                        sectHect: 0,
                        finanApa: false,
                      },
                      alliedCategory: {
                        id: 11,
                        name: 'Empresas',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      name: 'megantoli',
                      description: '',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                    },
                    actionLine: {
                      id: 23,
                      name: 'a',
                      rowspan: 1,
                      description: null,
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      objetive: {
                        id: 1,
                        masterPlan: {
                          id: 1,
                          name: 'Plan de Trabajo Nº 1',
                          description: '2015-2020',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                          version: 1,
                          active: true,
                          anp: {
                            id: 1,
                            name: 'Bosques Nublados de Udima',
                            withMasterPlan: 1,
                            fullName:
                              'Refugio de Vida Silvestre Bosques Nublados de Udima',
                            category: 'Refugio de Vida Silvestre',
                            district: 'Lima',
                            code: 'C01',
                          },
                        },
                        component: {
                          id: 6,
                          name: 'Ambiental',
                          description: '',
                          registrationDate: '2021-10-17T22:11:49.228-0500',
                          state: true,
                        },
                        description: 'Seguridad en el campo',
                        rowspan: 1,
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                        code: 'OB1',
                      },
                    },
                    description: 'vvvvvv',
                    rowspan: 1,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    indicator: '',
                    active: true,
                    progress: 0,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  workPlan: {
                    id: 1,
                    name: 'Plan de Trabajo N° 1',
                    description: null,
                    registrationDate: '2021-10-17T22:11:49.228-0500',
                    state: true,
                    year: 0,
                    version: 1,
                    active: true,
                    conservationAgreement: {
                      id: 192,
                      source: null,
                      anp: {
                        id: 1,
                        name: 'Bosques Nublados de Udima',
                        withMasterPlan: 1,
                        fullName:
                          'Refugio de Vida Silvestre Bosques Nublados de Udima',
                        category: 'Refugio de Vida Silvestre',
                        district: 'Lima',
                        code: 'C01',
                      },
                      districtId: '170101',
                      name: 'Acuerdo AC',
                      description: 'a',
                      registrationDate: '2021-10-17T22:11:49.228-0500',
                      state: true,
                      vigency: 1,
                      code: 'AC2021170001',
                      firm: '2021-10-11',
                      benPerson: '',
                      detailProduction: '',
                      restdet: '',
                      comment: '',
                      allied: false,
                      localization: 'a',
                      surfaceAmbito: 0,
                      surfaceIntervention: '',
                      hasMasterPlan: true,
                      hasDevelopmentPlan: true,
                      livePlan: 0,
                      institutionalPlan: 0,
                      forestZoning: 0,
                      detailMunicipality: '',
                      hasFirm: true,
                      hasWorkPlan: true,
                      hasActas: true,
                      hasMap: true,
                      hasShape: true,
                      hasMonitoring: true,
                      agreementState: {
                        id: 5,
                        name: 'Evaluación',
                        description: '',
                        registrationDate: '2021-10-17T22:11:49.228-0500',
                        state: true,
                      },
                      sectDet: '',
                      benIndirect: '',
                      producedArea: 0,
                      finanNum: 0,
                      areaAmbitc: 0,
                      partWomen: 0,
                      numFamily: 0,
                      partMen: 0,
                      territoryMod: '',
                      benFamily: '',
                      genObj: 'a',
                      fondName: '',
                      sectNom: '',
                      restHect: 0,
                      finanMod: '',
                      sectHect: 0,
                      finanApa: false,
                    },
                  },
                  name: 'e',
                  description: null,
                  registrationDate: '2021-10-17T22:11:49.228-0500',
                  state: true,
                  goal: 100,
                  active: null,
                  indicator: 'e',
                  semester: '1',
                  progress: 0,
                  trim1: true,
                  commitmentId: 4,
                  edit: false,
                  rowSpan: 1,
                  found: false,
                },
              ],
            };

            // this.monitoringList = this.setCheckBoxes(response.item.activities);
            // this.setTemporaryField(
            //   this.setCheckBoxes(response.item.activities)
            // );
            reponseFake = response.item;
            console.log(reponseFake);
            this.monitoringList = this.setCheckBoxes(reponseFake.activities);
            this.setTemporaryField(this.setCheckBoxes(reponseFake.activities));
          }
        });
    } catch (error) {
      this.alertService.error('Error al buscar monitoreos', 'Error', {
        autoClose: true,
      });
    }
  }
  comparer(otherArray) {
    return function (current) {
      return (
        otherArray.filter(function (other) {
          return other.id == current.id;
        }).length == 0
      );
    };
  }
  monitoringSearchHistory() {
    if (
      this.agreementId === undefined ||
      this.agreementId === null ||
      this.agreementId === ''
    ) {
      return;
    }
    try {
      this.workPlanService
        .monitoringSearchHistory(this.agreementId)
        .subscribe((response) => {
          console.log(['monitor history', response]);
          if (response && response.items.length > 0) {
            this.monitoringListHistory = response.items.map((act) => {
              console.log(response);
              const parsed = this.setCheckBoxes(act.activities);
              act.activities = parsed;
              return act;
            });
            this.monitoringHistoryCount = response.items.length;
          }
        });
    } catch (error) {
      this.alertService.error(
        'Error al traer el historial de monitoreos',
        'Error',
        {
          autoClose: true,
        }
      );
    }
  }
  setTemporaryField(items: any[]) {
    let count = 0;

    let rowLength = items.length;
    items = items.map((item, index) => {
      item.commitmentId = item.commitment.id;
      item.edit = false;
      item['rowSpan'] = 1;
      item['found'] = false;
      if (rowLength === index + 1) {
      } else {
        if (
          item.commitment.actionLine.objetive.description ===
          items[index + 1].commitment.actionLine.objetive.description
        ) {
          item['rowSpan'] += 1;
          item['found'] = false;
        } else {
          item['rowSpan'] = 1;
          count = 0;
        }
      }

      return item;
    });
    console.log(items);
    this.fieldArrayTotalTemp = items;
    this.fieldArrayMonitoringList = items;
  }
  monitoringSave() {
    this.submitted = true;
    this.disabled = true;
    if (this.monitoringList.length === 0) {
      this.disabled = false;
      return;
    }
    if (this.formMonitoring.invalid) {
      this.disabled = false;
      return;
    }
    let langArr = <FormArray>this.formMonitoring.get('activities');
    langArr.clear();
    this.monitoringList.forEach((item, index) => {
      langArr.push(this.fb.group(item));
    });
    console.log(this.formMonitoring.value);

    try {
      this.workPlanService
        .monitoringInsert(JSON.stringify(this.formMonitoring.value))
        .subscribe((response) => {
          if (response && response.success) {
            this.searchMonitoring();
            this.monitoringSearchHistory();
            this.alertService.success(
              'Se guardó correctamente el monitoreo',
              'Ok',
              {
                autoClose: true,
              }
            );
          } else {
            this.alertService.error(response.message, 'Error', {
              autoClose: true,
            });
          }
          this.submitted = false;
          this.disabled = false;
          this.resetFormMonitoring();
          this.modalRef.close();
        });
    } catch (error) {
      this.disabled = false;
      this.submitted = false;
      this.alertService.error('Error al guardar el monitoreo', 'Error', {
        autoClose: true,
      });
    }
  }
  resetFormMonitoring() {
    this.formMonitoring.reset();
    // this.formMonitoring.setValue({
    //   achievement: '',
    //   activities: new FormArray([]),
    //   description: '',
    //   comment: '',
    //   evaluation: '',
    //   recommendation: '',
    // });
    this.formMonitoring = this.fb.group({
      comment: ['', Validators.required],
      recommendation: ['', Validators.required],
      evaluation: ['', Validators.required],
      description: [''],
      achievement: ['', Validators.required],
      activities: new FormArray([]),
    });
  }
  activateMonitoring() {
    this.monitoringReady = true;
  }
  deActivateMonitoring() {
    this.monitoringReady = false;
  }
  ngOnDestroy() {}
}
