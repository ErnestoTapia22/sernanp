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
  editForm: FormGroup;
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
        items: [],
      },
    };
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])],
      description: ['', Validators.compose([Validators.required])],
      state: [true],
    });
    this.listTables();
  }
  get f() {
    return this.registerForm.controls;
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
      this.submitted = false;
    });
  }
  onUpdateModal(content, module, value) {
    this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });
    console.log({ content, module });
  }
  listTables() {
    try {
      if (this.modules && Object.keys(this.modules).length > 0) {
        for (const key in this.modules) {
          this.adminService.moduleList(key).subscribe((response) => {
            if (response && response.items && response.items.length > 0) {
              this.modules[key].items = response.items;
              console.log(this.modules);
            } else {
            }
          });
        }
      }
    } catch (error) {}
  }
  onSubmit(type) {
    this.submitted = true;
    if (type === 'register') {
      if (this.registerForm.invalid) return;
      console.log(this.registerForm.value);
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

      console.log(this.registerForm.value);
    } else {
    }
  }
  ngOnDestroy() {
    this.closeRegisterObserver.unsubscribe();
  }
}
