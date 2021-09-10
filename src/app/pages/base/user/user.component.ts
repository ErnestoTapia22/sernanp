import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '@app/_services/base/admin.service';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AlertService } from '../../../_services/base/alert.service';
import { UserService } from '@app/_services/auth/user.service';
import { Observable, Subscription } from 'rxjs';
import { AuthenticationService } from '@app/_services/auth/authentication.service';
import { User } from '@app/_models/auth/user';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
})
export class UserComponent implements OnInit, OnDestroy {
  profileList: any[] = [];
  moduleList: any[] = [];
  modalRef: NgbModalRef;
  insertForm: FormGroup;
  formInsertRoleModule: FormGroup;
  closeRegisterObserver: Subscription;
  submitted: boolean = false;
  formSearchUser: FormGroup;
  user: User;
  rolId: any;
  userList: any[] = [];
  formAddRole: FormGroup;
  constructor(
    private modalService: NgbModal,
    private modalDeleteService: NgbModal,
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private changeDetection: ChangeDetectorRef,
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.user = this.authenticationService.userValue;
    this.buildForms();
  }
  get f() {
    return this.insertForm.controls;
  }
  get g() {
    return this.formInsertRoleModule.controls;
  }
  get h() {
    return this.formSearchUser.controls;
  }
  get j() {
    return this.formAddRole.controls;
  }
  ngOnDestroy() {
    if (this.closeRegisterObserver !== undefined) {
      this.closeRegisterObserver.unsubscribe();
    }
  }
  onCreateModuleModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });

    this.getModuleList();
    this.listRoles();
  }
  onDeleteModal(content, id) {
    this.modalRef = this.modalDeleteService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
      backdropClass: 'custom-backdrop',
    });
    this.rolId = id;
  }
  onCreateProfileModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });

    // this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {});

    this.listRoles();
  }
  onCreateUserModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });
    this.listRoles();
  }
  getProfileList() {
    try {
      this.userService.moduleList(this.user.system).subscribe((response) => {
        if (response && response.items !== null && response.items.length > 0) {
          this.moduleList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer datos de los módulos', 'Error', {
        autoClose: true,
      });
    }
  }
  getModuleList() {
    try {
      this.userService.moduleList(this.user.system).subscribe((response) => {
        if (response && response.items !== null && response.items.length > 0) {
          this.moduleList = response.items;
          this.fillFormArray(this.moduleList);
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer datos de los módulos', 'Error', {
        autoClose: true,
      });
    }
  }
  fillFormArray(items) {
    this.selectedModules.clear();
    items.forEach((element) => {
      this.selectedModules.push(new FormControl(false));
    });
  }
  roleInsert() {
    this.submitted = true;
    if (this.insertForm.invalid) {
      // this.submitted = false;
      return;
    }
    try {
      this.userService
        .roleInsert(JSON.stringify(this.insertForm.value))
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(
              'Se guardaron correctamente los datos',
              'Ok',
              { autoClose: true }
            );
            this.insertForm.reset();
            this.listRoles();
          }
          this.submitted = false;
        });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('Error al guardar datos', 'Ok', {
        autoClose: true,
      });
    }
  }
  buildForms() {
    this.insertForm = this.formBuilder.group({
      id: [0],
      name: ['', Validators.compose([Validators.required])],
      flag: ['1'],
      system: [this.user.system],
    });
    this.formInsertRoleModule = this.formBuilder.group({
      id: [
        0,
        Validators.compose([Validators.required, Validators.pattern('[^0]+')]),
      ],
      modules: new FormArray([]),
    });
    this.formSearchUser = this.formBuilder.group({
      documentNumber: ['', Validators.required],
    });
    this.formAddRole = this.formBuilder.group({
      id: [0, Validators.required],
      role: this.formBuilder.group({
        id: [0, [Validators.required, Validators.pattern('[^0]+')]],
      }),
    });
  }
  listRoles() {
    try {
      this.userService.rolesList(this.user.system).subscribe((response) => {
        if (response && response.items !== null && response.items.length > 0) {
          this.profileList = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al listar los roles', 'Error', {
        autoClose: true,
      });
    }
  }
  roleDelete() {
    try {
      this.userService.roleDelete(this.rolId).subscribe((response) => {
        if (response.success == true) {
          this.alertService.success('Se la eliminado con éxito', 'Ok', {
            autoClose: true,
          });
        }
        this.modalRef.close();
      });
    } catch (error) {
      this.alertService.error('Error al eliminar el role', 'Error', {
        autoClose: true,
      });
    }
  }

  moduleSearchByRol(id) {
    try {
      this.userService.moduleSearchByRol(id).subscribe((response) => {
        if (response && response.items !== null && response.items.length > 0) {
          this.moduleChecked(response.items);
        } else {
          this.getModuleList();
        }
      });
    } catch (error) {
      this.alertService.error('Error al buscar módulos por rol', 'Error', {
        autoClose: true,
      });
    }
  }
  moduleChecked(items) {
    if (this.moduleList.length > 0) {
      this.moduleList.forEach((element, index) => {
        element['checked'] = false;
        items.forEach((item) => {
          if (item.id === element.id) {
            element['checked'] = true;
            let langArr = <FormArray>(
              this.formInsertRoleModule.controls['modules']
            );
            langArr.controls[index].patchValue(true);
          }
        });
      });
    }
  }
  onChangeProfile(id) {
    this.submitted = false;
    if (parseInt(id) > 0) {
      this.moduleSearchByRol(id);
      // this.moduleList.forEach((item, i) => {
      //   this.formInsertRoleModule.value.modules[i] = item.checked;
      // });
    } else {
      this.getModuleList();
    }
  }
  onCheckChange(event) {}
  insertRoleModule() {
    this.submitted = true;
    if (this.formInsertRoleModule.invalid) {
      return;
    }

    this.formInsertRoleModule.value.modules =
      this.formInsertRoleModule.value.modules
        .map((checked, i) => (checked ? { id: this.moduleList[i].id } : null))
        .filter((v) => v !== null);

    try {
      this.userService
        .rolModuleInsert(JSON.stringify(this.formInsertRoleModule.value))
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(
              'Se guardaron correctamente los datos',
              'Ok',
              { autoClose: true }
            );

            this.formInsertRoleModule.reset();

            this.modalRef.close();
          }
          this.submitted = false;
        });
    } catch (error) {
      this.alertService.error('Error al insertar módulos por rol', 'Error', {
        autoClose: true,
      });
    }
  }
  get selectedModules() {
    return this.formInsertRoleModule.controls.modules as FormArray;
  }
  onSearchUser() {
    this.submitted = true;
    try {
      if (this.formSearchUser.invalid) {
        return;
      }

      this.userService
        .userSearch(
          this.formSearchUser.get('documentNumber').value,
          this.user.system
        )
        .subscribe((response) => {
          console.log(response);
          if (response && response.items.length > 0) {
            this.userList = response.items;
          }
          this.submitted = false;
        });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('Error al buscar usuarios', 'Error', {
        autoClose: true,
      });
    }
  }
  addRole() {
    this.submitted = true;
    console.log(this.formAddRole.value);
    if (this.formAddRole.invalid) {
      return;
    }

    try {
      this.userService
        .userSave(JSON.stringify(this.formAddRole.value))
        .subscribe((response) => {
          if (response && response.success) {
            this.alertService.success(
              'Se registro correctamente al usuario',
              'Ok',
              { autoClose: true }
            );
            this.formAddRole.reset();
            this.modalRef.close();
          }
          this.submitted = false;
        });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('Error al guardar datos', 'Ok', {
        autoClose: true,
      });
    }
  }
  onChangeRol(id) {
    this.formAddRole.patchValue({
      id: id,
    });
  }
}
