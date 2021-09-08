import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '@app/_services/base/admin.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  closeRegisterObserver: Subscription;
  submitted: boolean = false;
  user: User;
  constructor(
    private modalService: NgbModal,
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
  ngOnDestroy() {}
  onCreateModuleModal(content) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });

    // this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {});
    this.getModuleList();
    this.listRoles();
  }
  getProfileList() {
    try {
      this.userService.moduleList(27).subscribe((response) => {
        console.log(response);
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
      this.userService.moduleList(27).subscribe((response) => {
        console.log(response);
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
}
