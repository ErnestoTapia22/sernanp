import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AnpService } from '@app/_services/masterplan/anp/anp.service';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { NgxSpinnerService } from 'ngx-spinner';
import { BehaviorSubject, Subscription } from 'rxjs';
import { AgreementService } from '../../../../_services/base/agreement.service';
import { AlertService } from '../../../../_services/base/alert.service';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit, OnDestroy {
  agreementList: any[];
  total: number;
  isLoading: boolean = false;
  pageSize: any;
  page: Number;
  form: FormGroup;
  moduleContext: any = {};
  modalRef: NgbModalRef;
  id: 0;
  closeRegisterObserver: Subscription;
  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });
  parsed: any;
  agreementStateList: any[] = [];
  agreementSourceList: any[] = [];
  departments: any[] = [];
  provinces: any[] = [];
  districts: any[] = [];
  anps: any[] = [];

  constructor(
    private modalService: NgbModal,
    private agreementService: AgreementService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder,
    private anpService: AnpService
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.total = 0;
    this.pageSize = 10;
    this.buildForm();
    // this.spinner.show();
    // setTimeout(() => {
    //   this.spinner.hide();
    // }, 5000);
    //this.spinner.show();
    // this.getItems();
    this.fillSelects();
    this.initQuery();
    this.onSearch();
    // this.fillSelects();
  }

  onDateSelect(e) {
    console.log(e);
  }
  onSearch() {
    try {
      this.isLoading = true;
      this.spinner.show();
      this.agreementService
        .agreementSearch(this.queryObserver.getValue())
        .subscribe((data) => {
          this.agreementList = [];
          if (data && data.items && data.items.length > 0) {
            this.agreementList = data.items;
            this.total = data.total;
            this.page = data.paginator.offset;
            this.pageSize = data.paginator.limit;
            this.isLoading = false;

            this.setTableHeight(this.pageSize, data.items.length);
          } else {
            this.isLoading = false;

            this.alertService.info('No se encontraron elementos', 'Ok', {
              autoClose: true,
            });
          }
          this.spinner.hide();
        });
    } catch (error) {
      this.isLoading = false;
      this.spinner.hide();
      this.alertService.error('Error al traer acuerdos:' + error, 'Error');
    }
  }
  parseData(parent, key, value) {
    const item = this.queryObserver.getValue();

    if (typeof item[parent] === 'string') {
      let parsed = JSON.parse(item[parent]);
      parsed[key] = value;
      item[parent] = JSON.stringify(parsed);
      this.queryObserver.next(item);
    }
  }

  get f() {
    return this.form.controls;
  }
  getPage(page: number) {
    this.parseData('paginator', 'offset', page);
    this.onSearch();
  }
  onChangePageSize(event) {
    // const q = this.queryObserver.getValue();
    // q.paginator['limit'] = this.f.pageSizes.value;
    this.parseData('paginator', 'limit', parseInt(this.f.pageSize.value));
    this.parseData('paginator', 'offset', 1);
    this.onSearch();
    // this.queryObserver.next({item:this.f.})
  }
  search(filters: any): void {
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters);
    console.log(q.item);
    this.queryObserver.next(q);

    this.onSearch();
  }
  searchProvinces(event) {
    const id = event;
    if (id == 0) {
      this.provinces = [];
      return;
    }
    this.form.patchValue({
      provinceId: '',
      districtId: '',
    });
    this.districts = [];
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
    this.form.patchValue({
      districtId: '',
    });
    try {
      this.agreementService.searchDistricts(id).subscribe((response) => {
        if (response && response.items.length > 0) {
          this.districts = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer las lineas de acci??n', 'Error', {
        autoClose: true,
      });
    }
  }
  buildForm(): void {
    this.form = this.fb.group({
      code: [''],
      name: [''],
      //state: [''],
      agreementState: this.fb.group({
        id: [0],
      }),
      anp: this.fb.group({
        id: [0],
      }),
      departmentId: [''],
      provinceId: [''],
      districtId: [''],
      firm: [''],
      firmEnd: [''],
      pageSize: ['10'],
    });
  }
  ngOnDestroy() {
    this.queryObserver.unsubscribe();
    if (this.closeRegisterObserver !== undefined)
      this.closeRegisterObserver.unsubscribe();
  }
  setTableHeight(rows, itemsLength?: number) {
    if (rows !== undefined && rows !== null) {
      const cm = document.getElementById('tableBody');
      if (itemsLength < rows) {
        const height = 50.838 * itemsLength;
        cm.setAttribute('style', `height:${height}px`);
      } else {
        const height = 50.838 * parseInt(rows);
        cm.setAttribute('style', `height:${height}px`);
      }
    }
  }
  fillSelects() {
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
      //this.agreementService.agreementSourceList().subscribe((response) => {
      //  if (
      //    response &&
      //    response.items !== undefined &&
      //    response.items !== null &&
      //    response.items.length > 0
      //  ) {
      //    this.agreementSourceList = response.items;
      //  }
      //});
      this.anpService.anpList().subscribe((response) => {
        if (response && response.items.length > 0) {
          this.anps = response.items;
        }
      });
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
      this.alertService.error(
        'Error al traer la lista de estados del acuerdo o la lista de fuente de financiamiento',
        'error',
        { autoClose: true }
      );
    }
  }
  clearForm() {
    this.form.reset({
      code: '',
      name: '',
      agreementState: { id: 0 },
      anp: { id: 0 },
      departmentId: '',
      provinceId: '',
      districtId: '',
      firm: '',
      firmEnd: '',
      pageSize: 10,
    });
    this.initQuery();
    this.onSearch();
  }
  initQuery() {
    let paginator = {
      limit: this.pageSize,
      offset: '1',
      sort: 'name',
      order: 'asc',
    };
    let item = {
      code: '',
      name: '',
      agreementState: { id: 0 },
      anp: { id: 0 },
      departmentId: '',
      provinceId: '',
      districtId: '',
      firm: '',
      firmEnd: '',
    };
    this.queryObserver.next({
      item: JSON.stringify(item),
      paginator: JSON.stringify(paginator),
    });
  }
  delete() {
    try {
      this.agreementService.delete(this.id).subscribe((response) => {
        if (response && response.success && response.success == true) {
          this.initQuery();
          this.onSearch();
          this.modalRef.close();
        } else
          this.alertService.error('No se ha podido eliminar', 'Error', {
            autoClose: true,
          });
      });
    } catch (error) {
      //this.submitted = false;
      this.alertService.error('error :' + error, 'Error', {
        autoClose: true,
      });
    }
  }
  onDeleteModal(content, id) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'sm',
      backdrop: 'static',
    });
    this.id = id;
    this.closeRegisterObserver = this.modalRef.closed.subscribe(() => {
      this.id = 0;
    });
  }
}
