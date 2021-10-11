import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { AgreementService } from '@app/_services/base/agreement.service';
import { AlertService } from '@app/_services/base/alert.service';
import { AnpService } from '@app/_services/masterplan/anp/anp.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { PdfService } from '@app/_services/report/pdf.service';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { BehaviorSubject, Observable, of } from 'rxjs';
import printPdf from '@app/print/pdf/index';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import Zoom from '@arcgis/core/widgets/Zoom';

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
})
export class ReportsComponent implements OnInit {
  agreementList: any[];
  total: number;
  isLoading: boolean = false;
  pageSize: any;
  page: Number;
  form: FormGroup;
  modalRef: NgbModalRef;
  url: string =
    'https://i.postimg.cc/QCJnRkYt/Whats-App-Image-2021-08-30-at-6-31-19-PM.jpg';
  urlSafe: SafeResourceUrl;

  queryObserver = new BehaviorSubject({
    item: '',
    paginator: '',
  });
  agreementId: string = '0';
  commitmentsList: any[] = [];
  mapProperties: any;
  mapViewProperties: any;
  agreementDetail: object = {
    department: [{ name: '' }],
    province: [{ name: '' }],
    district: [{ name: '' }],
  };
  districts: any[] = [];
  departments: any[] = [];
  provinces: any[] = [];
  agreementStateList: any[] = [];
  alliedList: any[] = [];
  anps: any[] = [];
  constructor(
    public sanitizer: DomSanitizer,
    private agreementService: AgreementService,
    private alertService: AlertService,
    private spinner: NgxSpinnerService,
    private fb: FormBuilder,
    private pdfService: PdfService,
    private modalService: NgbModal,
    private anpService: AnpService
  ) {}

  ngOnInit(): void {
    this.page = 1;
    this.total = 0;
    this.pageSize = 10;
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 9,
    };

    this.buildForm();
    this.fillSelects();
    this.initQuery();
    this.onSearch();
    this.urlSafe = this.sanitizer.bypassSecurityTrustResourceUrl(this.url);
  }
  onMapInit({ map, view }) {
    view.ui.remove('zoom');
  }

  downloadPdf(content, append: boolean, delimiter) {
    this.pdfService.makePDF(content);
    // this.pdfService.sendToPdf('ResportePDF', this.agreementId, content);
    // const domClone = content.cloneNode(true);
    // let $printSection = document.getElementById('printSection');
    // if (!$printSection) {
    //   $printSection = document.createElement('div');
    //   $printSection.id = 'printSection';
    //   document.body.appendChild($printSection);
    // }
    // if (append !== true) {
    //   $printSection.innerHTML = '';
    // } else if (append === true) {
    //   if (typeof delimiter === 'string') {
    //     $printSection.innerHTML += delimiter;
    //   } else if (typeof delimiter === 'object') {
    //     $printSection.appendChild(delimiter);
    //   }
    // }
    // $printSection.appendChild(domClone);
    // window.print();
  }
  search(filters: any): void {
    const q = this.queryObserver.getValue();
    q.item = JSON.stringify(filters);
    console.log(q.item);
    this.queryObserver.next(q);

    this.onSearch();
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
            this.spinner.hide();
            this.setTableHeight(this.pageSize, data.items.length);
          } else {
            this.isLoading = false;
            this.spinner.hide();
            this.alertService.info('No se encontraron elementos', 'Ok', {
              autoClose: true,
            });
          }
        });
    } catch (error) {
      this.isLoading = false;
      this.spinner.hide();
      this.alertService.error('Error al traer acuerdos:' + error, 'Error');
    }
  }
  searchProvinces(event) {
    const id = event;
    this.form.patchValue({
      provinceId: '',
      districtId: '',
    });
    if (id == 0) {
      this.provinces = [];
      return;
    }
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
    this.form.patchValue({
      districtId: '',
    });
    if (id == 0) {
      this.districts = [];
      return;
    }
    try {
      this.agreementService.searchDistricts(id).subscribe((response) => {
        if (response && response.items.length > 0) {
          this.districts = response.items;
        }
      });
    } catch (error) {
      this.alertService.error('Error al traer las lineas de acción', 'Error', {
        autoClose: true,
      });
    }
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
  buildForm(): void {
    this.form = this.fb.group({
      code: ['', Validators.compose([Validators.maxLength(10)])],
      name: [''],
      firm: [
        '',
        Validators.compose([
          Validators.pattern(
            '/^(((0[1-9]|[12]d|3[01])/(0[13578]|1[02])/((19|[2-9]d)d{2}))|((0[1-9]|[12]d|30)/(0[13456789]|1[012])/((19|[2-9]d)d{2}))|((0[1-9]|1d|2[0-8])/02/((19|[2-9]d)d{2}))|(29/02/((1[6-9]|[2-9]d)(0[48]|[2468][048]|[13579][26])|(([1][26]|[2468][048]|[3579][26])00))))$/g'
          ),
        ]),
      ],
      firmEnd: [''],
      state: [''],
      pageSize: ['10'],
      agreementState: this.fb.group({
        id: [0],
      }),
      anp: this.fb.group({
        id: [0],
      }),
      departmentId: [''],
      provinceId: [''],
      districtId: [''],
    });
  }
  ngOnDestroy() {
    this.queryObserver.unsubscribe();
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
  onReportPdf(content, id) {
    this.modalRef = this.modalService.open(content, {
      centered: true,
      size: 'lg',
      backdrop: 'static',
    });
    this.agreementId = id;
    this.commitmentsList = [];
    this.alliedList = [];
    this.getDetail();
  }
  getDetail() {
    if (
      this.agreementId === null &&
      this.agreementId === undefined &&
      this.agreementId === ''
    ) {
      this.alertService.error('No se encontro el id del acuerdo', 'Error', {
        autoClose: true,
      });
      return;
    }
    try {
      this.agreementService
        .agreementDetail(this.agreementId)
        .subscribe((response) => {
          if (response && response.item !== null) {
            console.log(response);

            this.agreementDetail = response.item;
            this.agreementDetail['department'] = [{ name: '' }];
            this.agreementDetail['province'] = [{ name: '' }];
            this.agreementDetail['district'] = [{ name: '' }];
            this.fillSelects2(response.item.districtId);

            this.searchAllied();

            this.searchCommitments();
          }
        });
    } catch (error) {
      this.alertService.error(
        'Error al traer el detalle del acuerdo',
        'Error',
        { autoClose: true }
      );
    }
  }
  fillSelects2(districtId) {
    if (districtId === null || districtId === undefined) {
      return;
    }
    try {
      this.agreementService.departmentList().subscribe((response) => {
        if (
          response &&
          response.items !== undefined &&
          response.items !== null &&
          response.items.length > 0
        ) {
          console.log(response);
          this.agreementDetail['department'] = response.items.filter((item) => {
            return item.code === districtId.substring(0, 2);
          });
        }
      });
      this.agreementService
        .searchProvinces(districtId.substring(0, 2))
        .subscribe((response) => {
          if (
            response &&
            response.items !== undefined &&
            response.items !== null &&
            response.items.length > 0
          ) {
            this.agreementDetail['province'] = response.items.filter((item) => {
              return item.code === districtId.substring(0, 4);
            });
          }
        });
      this.agreementService
        .searchDistricts(districtId.substring(0, 4))
        .subscribe((response) => {
          if (
            response &&
            response.items !== undefined &&
            response.items !== null &&
            response.items.length > 0
          ) {
            this.agreementDetail['district'] = response.items.filter((item) => {
              return item.code === districtId;
            });
            console.log(this.agreementDetail);
          }
        });
    } catch (error) {
      this.alertService.error('Error al traer data del acuerdo', 'Error', {
        autoClose: true,
      });
    }
  }
  searchAllied() {
    if (
      this.agreementId === '0' ||
      this.agreementId === '' ||
      this.agreementId === null
    ) {
      return;
    }

    try {
      this.agreementService
        .alliedSearch(parseInt(this.agreementId))
        .subscribe((response) => {
          console.log(response);
          if (response && response.items.length > 0) {
            this.alliedList = response.items;
          }
        });
    } catch (error) {
      this.alertService.error('Error al traer los suscriptores', 'error', {
        autoClose: true,
      });
    }
  }
  searchCommitments() {
    if (
      this.agreementId === '' ||
      this.agreementId === null ||
      this.agreementId === '0'
    ) {
      return;
    }
    try {
      this.agreementService
        .commitmentsSearch(this.agreementId)
        .subscribe((response) => {
          if (response && response.items.length > 0) {
            this.commitmentsList = response.items;
            console.log(this.commitmentsList);
          }
        });
    } catch (error) {
      this.alertService.error('Error al insertar los compromisos', 'error', {
        autoClose: true,
      });
    }
  }
  clearForm() {
    this.form.reset({
      agreementState: { id: 0 },
      anp: { id: 0 },
      departmentId: '',
      provinceId: '',
      districtId: '',
      code: '',
      name: '',
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
}
