import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { AdminService } from '../../_services/admin/admin.service';
import { AlertService } from '../../_services/base/alert.service';

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
    private alertService: AlertService,
    private changeDetection: ChangeDetectorRef
  ) {
    this.modules = {
      agreementstate: {
        name: 'Estado de Acuerdo',
        ownName: 'agreementstate',
        id: 0,
        items: [],
        item: {},
      },
      source: {
        name: 'Fuente',
        ownName: 'source',
        id: 0,
        items: [],
        item: {},
      },
      alliedcategory: {
        name: 'Categoría aliado',
        ownName: 'alliedcategory',
        id: 0,
        items: [],
        item: {},
      },
      component: {
        name: 'Componente',
        ownName: 'component',
        id: 0,
        items: [],
        item: {},
      },
    };
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.required])],
      description: [''],
      state: [true],
    });
    this.updateForm = this.formBuilder.group({
      id: [0, Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      description: [''],
      state: [true],
    });
    console.log(localStorage.getItem('auth'));
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
    console.log({ content, id, module });
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
              this.modules[key].items = [];
              this.modules[key].items = response.items;
            } else {
              this.modules[key].items = [];
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
          if (response.success == true) {
            this.listTables();
            this.modalRef.close();
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
    if (this.closeRegisterObserver !== undefined)
      this.closeRegisterObserver.unsubscribe();
  }
}
