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
    }

    this.searchCommitments();

    // this.onSearch();
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
      agreementId: [0],
      anp: this.fb.group({
        id: [{ value: '', disabled: true }],
      }),
      commitments: new FormArray([
        this.fb.group({
          id: [0],
          activities: new FormArray([
            this.fb.group({
              id: [0],
              activity: [''],
              indicator: [''],
              goal: 0,
              semester: [1],
            }),
          ]),
        }),
      ]),
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
          console.log(response);
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
    console.log(this.fieldArray);

    console.log(this.workPlanForm.value);
  }
  buildItem() {
    const newFieldArray = this.fieldArray.map((obj) => {
      const o = Object.assign({}, obj);
      o.semester = [];
      if (o.hasOwnProperty('trim1')) {
        if (o.trim1) {
          o.semester.push(1);
        }
      }

      if (o.hasOwnProperty('trim2')) {
        if (o.trim2) {
          o.semester.push(2);
        }
      }

      return o;
    });
    for (let commitment of this.commitmentsList) {
      for (let activity of newFieldArray) {
      }

      const formArray = this.workPlanForm.get('commitments') as FormArray;
      formArray.push(new FormGroup(commitment));
    }

    console.log(newFieldArray);
  }
  activityListByCommitment() {
    if (
      this.commitmentId === 0 ||
      this.commitmentId === undefined ||
      this.commitmentId === null
    ) {
      return;
    }
    console.log(this.fieldArrayTotalTemp);
    const activitiesFound = this.fieldArrayTotalTemp.find(
      (x) => x.commitmentId == this.commitmentId
    );
    console.log(activitiesFound);
    if (activitiesFound === undefined || activitiesFound === null) {
      try {
        this.workPlanService
          .activityListByCommitment(this.commitmentId)
          .subscribe((response) => {
            if (response && response.items.length > 0) {
              console.log(response);
              this.fieldArray = response.items;
            }
          });
      } catch (error) {
        this.alertService.error(
          'Error al traer la lista de actividades',
          'Error',
          { autoClose: true }
        );
      }
    } else {
      this.fieldArray = this.fieldArrayTotalTemp.filter(
        (x) => x.commitmentId === this.commitmentId
      );
    }
  }
  saveActivityTemp() {
    console.log(this.fieldArray);
    this.fieldArray.map((activity) => {
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

    console.log(this.fieldArrayTotalTemp);
  }
  cleanTempField() {
    this.fieldArrayTotalTemp = [];
  }
  ngOnDestroy() {}
}
