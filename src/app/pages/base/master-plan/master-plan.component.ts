import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MasterPlanService } from '@app/_services/base/master-plan.service';
import { AlertService } from '../../../_services/base/alert.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-master-plan',
  templateUrl: './master-plan.component.html',
  styleUrls: ['./master-plan.component.css'],
})
export class MasterPlanComponent implements OnInit, OnDestroy {
  form: FormGroup;
  commitments: any[];
  isLoading: Boolean = false;
  anpId: number = 0;
  goalsList: any[] = [];
  insertGoals: FormGroup;
  insertLineAction: FormGroup;
  modalRef: NgbModalRef;
  submitted: boolean = false;
  closeRegisterObserver: Subscription;
  actionLinesList: any[] = [];
  objectiveId: number = 0;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private masterPlanService: MasterPlanService,
    private alertService: AlertService,
    private modalService: NgbModal
  ) {}
  get f() {
    return this.form.controls;
  }
  get g() {
    return this.insertGoals.controls;
  }
  get h() {
    return this.insertLineAction.controls;
  }
  ngOnInit(): void {
    if (
      this.route.snapshot.paramMap.get('id') !== undefined &&
      this.route.snapshot.paramMap.get('id') !== null &&
      this.route.snapshot.paramMap.get('id') !== ''
    )
      this.anpId = parseInt(this.route.snapshot.paramMap.get('id'));

    this.buildForms();
    this.getDetail();
  }
  buildForms() {
    this.form = this.fb.group({
      code: [''],
      name: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])],
      id: [],
      state: [true],
      anp: [{ id: this.anpId || 0 }],
      active: [true],
      version: [1],
    });
    this.insertGoals = this.fb.group({
      component: [1, Validators.compose([Validators.required])],
      code: [''],
      description: ['', Validators.compose([Validators.required])],
      masterPlan: [0],
    });
    this.insertLineAction = this.fb.group({
      objetive: [{ id: 0 }],
      name: ['', Validators.compose([Validators.required])],
      description: [''],
    });
  }
  getDetail() {
    try {
      // this.anpId = this.route.snapshot.paramMap.get('id');
      if (this.anpId) {
        this.masterPlanService
          .masterPlanDetailByAnp(this.anpId)
          .subscribe((response) => {
            if (
              response &&
              response.item !== null &&
              response.item !== undefined
            ) {
              this.form.setValue({
                id: response.item.id,
                name: response.item.name,
                description: response.item.description,
                state: response.item.state,
                code: response.item.code,
                anp: { id: this.anpId },
                active: true,
                version: 1,
              });
              this.getGoalsList();
            }
          });
      }
    } catch (error) {
      this.alertService.error('error:' + error, 'Error');
    }
  }
  getGoalsList() {
    try {
      this.masterPlanService
        .masterPlanObjetiveList(this.form.get('id').value)
        .subscribe((response) => {
          if (
            response &&
            response.items !== null &&
            response.items !== undefined &&
            response.items.length > 0
          ) {
            this.goalsList = response.items;
          }
        });
    } catch (error) {
      this.alertService.error('error : ' + error, 'Error');
    }
  }
  saveGoals() {
    try {
      this.submitted = true;
      if (this.insertGoals.invalid) {
        return;
      }
      this.insertGoals.patchValue({
        masterPlan: { id: this.form.get('id').value },
      });
      this.insertGoals.patchValue({
        component: { id: this.insertGoals.get('component').value },
      });

      this.masterPlanService
        .goalsInsert(JSON.stringify(this.insertGoals.value))
        .subscribe((response) => {
          if (response && response.success === true) {
            this.submitted = false;
            this.modalRef.close();
            this.getGoalsList();
          }
        });
    } catch (error) {
      this.submitted = false;
      this.modalRef.close();
      this.alertService.error('error :' + error, 'Error');
    }
  }
  onRegisterModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });

    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.insertGoals.reset({});

      this.submitted = false;
    });
  }
  ngOnDestroy() {
    if (this.closeRegisterObserver !== undefined)
      this.closeRegisterObserver.unsubscribe();
  }
  onRegisterLineActionModal(content, id) {
    console.log(id);
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });

    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.insertLineAction.reset({});

      this.submitted = false;
    });
    this.actionLinesList = [];
    this.objectiveId = id;
    this.actionLineList(id);
  }
  actionLineList(id) {
    try {
      this.masterPlanService.actionLineList(id).subscribe((response) => {
        console.log(response);
        if (
          response &&
          response.items !== null &&
          response.items !== undefined &&
          response.items.length > 0
        ) {
          this.actionLinesList = response.items;
        }
      });
    } catch (error) {
      this.modalRef.close();
      this.alertService.error('error :' + error, 'Error');
    }
  }
  saveLineAction() {
    try {
      this.submitted = true;
      if (this.insertLineAction.invalid) {
        return;
      }
      this.insertLineAction.patchValue({
        objetive: { id: this.objectiveId },
      });

      this.masterPlanService
        .actionLineInsert(JSON.stringify(this.insertLineAction.value))
        .subscribe((response) => {
          if (response && response.success === true) {
            this.submitted = false;
            this.actionLineList(this.objectiveId);
          }
        });
    } catch (error) {
      this.submitted = false;
      this.modalRef.close();
      this.alertService.error('error :' + error, 'Error');
    }
  }
  deleteActionLine(id) {
    this.submitted = true;
    try {
      this.masterPlanService.actionLineDelete(id).subscribe((response) => {
        if (response && response.success && response.success == true) {
          this.submitted = false;
          this.actionLinesList = [];
          this.actionLineList(this.objectiveId);
        }
      });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('error :' + error, 'Error');
    }
  }
  insertMasterPlan() {
    try {
      this.submitted = true;
      if (this.form.invalid) {
        return;
      }

      this.masterPlanService
        .masterPlanInsert(JSON.stringify(this.form.value))
        .subscribe((response) => {
          this.submitted = false;
          if (response && response.success === true) {
            this.alertService.success('Se guardo correctamente', 'Ok');
            this.getDetail();
          } else {
            this.alertService.info('Ya existe el plan maestro', 'Ok');
          }
        });
    } catch (error) {
      this.submitted = false;
      this.formReset();
      this.alertService.error('error :' + error, 'Error');
    }
  }
  formReset() {
    this.form.reset();
  }
}
