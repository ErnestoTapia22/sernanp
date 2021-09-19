import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../_services/admin/admin.service';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AlertService } from '../../_services/base/alert.service';
import { UserService } from '@app/_services/auth/user.service';
import { Observable, of, Subscription, BehaviorSubject } from 'rxjs';
import { AuthenticationService } from '@app/_services/auth/authentication.service';
import { User } from '@app/_models/auth/user';
import { NgxSpinnerService } from 'ngx-spinner';

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
  formUserSearchPrimary: FormGroup;
  userListPrimary: any[] = [];
  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });
  total: number;

  pageSize: any;
  page: Number;
  constructor(
    private modalService: NgbModal,
    private modalDeleteService: NgbModal,
    private adminService: AdminService,
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private changeDetection: ChangeDetectorRef,
    private userService: UserService,
    private authenticationService: AuthenticationService,
    private spinner: NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.user = this.authenticationService.userValue;
    this.buildForms();
    this.initQuery();
    this.listRoles();
    this.onSearchUserPrimary();
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
  get k() {
    return this.formUserSearchPrimary.controls;
  }
  ngOnDestroy() {
    if (this.closeRegisterObserver !== undefined) {
      this.closeRegisterObserver.unsubscribe();
      this.queryObserver.unsubscribe();
    }
  }
  onCreateModuleModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });

    this.getModuleList();
    // this.listRoles();
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

    // this.listRoles();
  }
  onCreateUserModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });
    // this.listRoles();
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
            this.resetFormRole();
            this.listRoles();
          }          
        });
    } catch (error) {      
      this.alertService.error('Error al guardar datos', 'Error', {
        autoClose: true,
      });
    }
  }
  resetFormRole(){
    this.insertForm = this.formBuilder.group({
      id: [0],
      name: ['', Validators.compose([Validators.required])],
      flag: ['1'],
      system: [this.user.system],
    });
  }
  buildForms() {
    this.resetFormRole();
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
    this.formUserSearchPrimary = this.formBuilder.group({
      item: this.formBuilder.group({
        system: [this.user.system, Validators.required],
        name: [''],
        lastName: [''],
        userName: [''],
        role: this.formBuilder.group({
          id: [0],
        }),
      }),
      paginator: this.formBuilder.group({
        offset: [1],
        limit: [5],
        sort: [''],
        order: ['asc'],
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
        this.listRoles();
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
    //this.submitted = true;
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
          //this.submitted = false;
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
    try {
      if (this.formSearchUser.invalid) {
        this.alertService.error('Error al buscar el usuario', 'Error', {
          autoClose: true,
        });
        return;
      }   
      this.submitted = true;   
      this.userService
        .userSearch(
          this.formSearchUser.get('documentNumber').value,
          this.user.system
        )
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.userList = response.items;
            this.formAddRole.patchValue({
              role: { id: response.items[0].role.id || 0 },
            });
          }
          else this.alertService.error('No se encontraron resultados para la búsqueda', 'Error', {
            autoClose: true,
          });
          this.submitted = false;
        });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('Error al buscar usuarios', 'Error', {
        autoClose: true,
      });
    }
  }
  assignedUserClose(){
    this.modalRef.close();
    this.userList = [];
    this.formAddRole.reset();
    this.formSearchUser.reset();
  }
  addRole() {
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
            this.formSearchUser.reset();
            this.modalRef.close();
            this.userList = [];
            this.onSearchUserPrimary();
          }
          //this.submitted = false;
        });
    } catch (error) {
      //this.submitted = false;
      this.alertService.error('Error al guardar datos', 'Error', {
        autoClose: true,
      });
    }
  }
  onChangeRol(id) {
    this.formAddRole.patchValue({
      id: id,
    });
  }
  onSearchUserPrimary() {
    this.submitted = true;
    this.spinner.show();
    this.userListPrimary = [];
    try {
      this.userService
        .userSearchPrimary(this.queryObserver.getValue())
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.userListPrimary = response.items;
            this.total = response.total;
            this.page = response.paginator.offset;
            this.pageSize = response.paginator.limit;
          }
          else this.alertService.error('No se encontraron resultados para la búsqueda', 'Error', {
            autoClose: true,
          });
          this.spinner.hide();
          this.submitted = false;
        });
    } catch (error) {
      this.submitted = false;
      this.alertService.error('Error al buscar usuario', 'Error', {
        autoClose: true,
      });
    }
  }
  onDeleteUserPrimary(id) {
    try {
      this.userService.userDelete(id).subscribe((response) => {
        if (response.success == true) {
          this.alertService.success('Se la eliminado con éxito', 'Ok', {
            autoClose: true,
          });
        }
        this.onSearchUserPrimary();
      });
    } catch (error) {
      this.alertService.error('Error al eliminar el role', 'Error', {
        autoClose: true,
      });
    }
  }
  getPage(page: number) {
    this.parseData('paginator', 'offset', page);
    this.onSearchUserPrimary();
  }
  onChangePageSize(event) {
    // const q = this.queryObserver.getValue();
    // q.paginator['limit'] = this.f.pageSizes.value;
    this.parseData('paginator','limit',parseInt(this.formUserSearchPrimary.get('paginator.limit').value));
    this.parseData('paginator', 'offset', 1);
    // this.onSearch();
    // this.queryObserver.next({item:this.f.})
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

  initQuery() {
    let paginator = this.formUserSearchPrimary.get('paginator').value;
    let item = this.formUserSearchPrimary.get('item').value;
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
  }
  searchUserPrimary(filters: any): void {
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters.item);
    this.queryObserver.next(q);
    this.onSearchUserPrimary();
  }
  clearForm() {
    this.formUserSearchPrimary.reset({
      item: {
        system: this.user.system,
        name: '',
        lastName: '',
        userName: '',
        role: { id: 0 },
      },
      paginator: {
        offset: 1,
        limit: 5,
        sort: '',
        order: 'asc',
      },
    });
    this.initQuery();
    this.onSearchUserPrimary();
  }
}
