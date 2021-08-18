import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminService } from '../../../_services/base/admin.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
})
export class AdminComponent implements OnInit {
  modules: any = {};
  constructor(
    private modalService: NgbModal,
    private adminService: AdminService
  ) {
    this.modules = {
      economicactivity: { name: 'Actividad económica', items: [] },
    };
  }

  ngOnInit(): void {
    this.listTables();
  }
  onRegisterModal(content, module) {
    this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });
  }
  onUpdateModal(content, module) {
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
}
