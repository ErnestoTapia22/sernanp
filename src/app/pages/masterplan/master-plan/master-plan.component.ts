import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MasterPlanService } from '../../../_services/masterplan/masterplan/master-plan.service';
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
  withMasterPlan: Boolean = false;
  isRew: Boolean = false;
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
    ){
      this.anpId = parseInt(this.route.snapshot.paramMap.get('id'));
      console.log(this.route.snapshot.paramMap.get('withMasterPlan'));
      if (this.route.snapshot.paramMap.get('withMasterPlan')=="1")
        this.withMasterPlan = true;
      else
        this.withMasterPlan = false;
      if (this.route.snapshot.paramMap.get('withMasterPlan')=="2")
        this.isRew = true;
    }
    this.buildForms();
    this.getDetail();
  }
  buildForms() {
    this.form = this.fb.group({
      code: ['', Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])],
      id: [0, Validators.compose([Validators.required])],
      state: [true],
      anp: [{ id: this.anpId || 0 }],
      active: [true],
      version: [1]
    });    
    this.resetActionLine();
  }  
  formReset() {
    this.form.reset();
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
            this.alertService.success('Se guardo correctamente', 'Ok', {
              autoClose: true,
            });
            this.getDetail();
          } else {
            this.alertService.info('Ya existe el plan maestro', 'Ok', {
              autoClose: true,
            });
          }
        });
    } catch (error) {
      this.submitted = false;
      this.formReset();
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }
  getDetail() {
    try {
      if (this.anpId && !this.isRew) {
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
              this.withMasterPlan = true;
              this.getGoalsList();
            }
          });
      }
    } catch (error) {
      this.alertService.error('error:' + error, 'Error', {
        autoClose: true,
      });
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
      this.alertService.error('error : ' + error, 'Error', {
        autoClose: true,
      });
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
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }
  deleteObjetive(id){
    this.submitted = true;
    try {
      this.masterPlanService.commitmentDelete(id).subscribe((response) => {
        if (response && response.success && response.success == true) {
          this.submitted = false;
          this.getGoalsList();
        }
        else this.alertService.error('No se ha podido eliminar', 'Error', {
          autoClose: true,
        });
      });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }
  onRegisterModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.insertGoals = this.fb.group({
      component: ['', Validators.compose([Validators.required])],
      code: [''],
      description: ['', Validators.compose([Validators.required])],
      masterPlan: [0],
      state: [true]
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
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }
  resetActionLine(){
    this.insertLineAction = this.fb.group({
      objetive: [{ id: 0 }],
      name: ['', Validators.compose([Validators.required])],
      description: [''],
      state: [true]
    });
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
            this.insertLineAction.reset();
            this.resetActionLine();
          }
        });
    } catch (error) {
      this.submitted = false;
      this.modalRef.close();
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
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
        else this.alertService.error('No se ha podido eliminar', 'Error', {
          autoClose: true,
        });
      });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }  
}
