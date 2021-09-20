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
      distritoId: [''],
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
      description: ['', Validators.required],
      achievement: [''],
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
            localization: '',
            distritoId: response.item.distritoId,
            department: 0,
            province: 0,
            district: 0,
            description: response.item.description,
          });
          this.setLocalization(response.item.distritoId);
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
    this.fieldArray.push(this.newAttribute);
    this.newAttribute = {};
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

    const activitiesFound = this.fieldArrayTotalTemp.find(
      (x) => x.commitmentId == this.commitmentId
    );
    console.log(activitiesFound);
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
  setCheckBoxes2(items: any[]) {
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
      activity.commitment = { id: this.commitmentId };
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
            this.monitoringList = this.setCheckBoxes(response.item.activities);
            this.setTemporaryField(
              this.setCheckBoxes(response.item.activities)
            );
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
          if (response && response.items.length > 0) {
            this.monitoringListHistory = response.items.map((act) => {
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
    items = items.map((item) => {
      item.commitmentId = item.commitment.id;
      item.edit = false;
      return item;
    });
    this.fieldArrayTotalTemp = items;
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
    this.formMonitoring.setValue({
      comment: '',
      recommendation: '',
      evaluation: '',
      description: '',
      achievement: '',
      activities: [],
    });
  }
  ngOnDestroy() {}
}
