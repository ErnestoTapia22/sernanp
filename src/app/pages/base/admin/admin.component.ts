import { Component, OnInit, OnDestroy } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../../_services/base/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../_services/base/alert.service';

import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit, OnDestroy {
  registerForm: FormGroup;
  updateForm: FormGroup;
  modules: any = {};
  moduleContext: any = {};
  submitted: boolean = false;
  modalRef: NgbModalRef;
  closeRegisterObserver: Subscription;
  constructor(
    private modalService: NgbModal,
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private alertService: AlertService
  ) {
    this.modules = {
      economicactivity: {
        name: 'Actividad económica',
        ownName: 'economicactivity',
        id: 0,
        items: [],
        item: {},
      },
    };
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])],
      state: [true],
    });
    this.updateForm = this.formBuilder.group({
      id: [0, Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])],
      state: [true],
    });
    this.listTables();
  }
  get f() {
    return this.registerForm.controls;
  }
  get g() {
    return this.updateForm.controls;
  }
  onRegisterModal(content, module) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.moduleContext = {};
    if (this.modules[module] !== undefined)
      this.moduleContext = this.modules[module];
    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.registerForm.reset({
        state: this.registerForm.get('state').value,
      });
      this.moduleContext = {};
      this.submitted = false;
    });
  }
  onUpdateModal(content, id, module) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.moduleContext = {};
    if (this.modules[module] !== undefined)
      this.moduleContext = this.modules[module];
    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.updateForm.reset({
        state: this.updateForm.get('state').value,
      });
      this.moduleContext = {};
      this.submitted = false;
    });
    this.getDetail(module, id);
  }
  onDeleteModal(content, id, module) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.moduleContext = {};
    if (this.modules[module] !== undefined)
      this.moduleContext = this.modules[module];
    this.moduleContext.id = id;
    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.moduleContext = {};
    });
  }
  listTables() {
    try {
      if (this.modules && Object.keys(this.modules).length > 0) {
        for (const key in this.modules) {
          this.adminService.moduleList(key).subscribe((response) => {
            if (response && response.items && response.items.length > 0) {
              this.modules[key].items = response.items;
            } else {
            }
          });
        }
      }
    } catch (error) {}
  }
  getDetail(module, id) {
    try {
      this.adminService.moduleDetail(module, id).subscribe((response) => {
        console.log(response);
        if (response && response.item) {
          this.updateForm.setValue({
            id: response.item.id,
            name: response.item.name,
            description: response.item.description,
            state: response.item.state,
          });
        }
      });
    } catch (error) {
      // this.modalRef.close();
      this.alertService.error('error al traer detalle:' + error, 'Error');
    }
  }
  onSubmit(type) {
    this.submitted = true;
    if (type === 'register') {
      if (this.registerForm.invalid) return;
      try {
        this.adminService
          .moduleRegister(
            this.moduleContext.ownName,
            JSON.stringify(this.registerForm.value)
          )
          .subscribe((data) => {
            if (data && data.extra !== undefined && data.extra !== null) {
              this.registerForm.reset({
                state: this.registerForm.get('state').value,
              });
              this.submitted = false;
              this.modalRef.close();
              this.listTables();
            } else {
              this.submitted = false;
              this.modalRef.close();
            }
          });
      } catch (error) {
        this.submitted = false;
        this.modalRef.close();
        this.alertService.error('error al grabar datos:' + error, 'Error');
      }
    } else {
      if (this.updateForm.invalid) {
        return;
      }
      try {
        this.adminService
          .moduleUpdate(
            this.moduleContext.ownName,
            JSON.stringify(this.updateForm.value)
          )
          .subscribe((response) => {
            if (
              response &&
              response.extra !== undefined &&
              response.extra !== null
            ) {
              this.updateForm.reset({
                state: this.updateForm.get('state').value,
              });
              this.submitted = false;
              this.modalRef.close();
              this.listTables();
            } else {
              this.submitted = false;
              this.modalRef.close();
            }
          });
      } catch (error) {
        this.submitted = false;
        this.modalRef.close();
        this.alertService.error('error al modificar datos:' + error, 'Error');
      }
    }
  }
  delete() {
    try {
      this.adminService
        .moduleDelete(this.moduleContext.ownName, this.moduleContext.id)
        .subscribe((response) => {
          if (response && response.success === true) {
            this.modalRef.close();
            this.listTables();
          } else {
            this.modalRef.close();
          }
        });
    } catch (error) {
      this.modalRef.close();
      this.alertService.error('Error al eliminar registro:' + error, 'Error');
    }
  }
  ngOnDestroy() {
    this.closeRegisterObserver.unsubscribe();
  }
}
