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

import pdfMake from 'pdfmake/build/pdfmake';
import pdfFonts from 'pdfmake/build/vfs_fonts';
import MapView from '@arcgis/core/views/MapView';
pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-reports',
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.css'],
})
export class ReportsComponent implements OnInit {
  pdfMake: pdfMake;
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
  view: MapView;
  photoUrl: string = '';
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
    this.view = view;
  }

  downloadPdf(content, append: boolean, delimiter) {
    // this.pdfService.makePDF(content);
    this.agreementService
      .agreementPdf(this.agreementId)
      .subscribe((response) => {
        const file = new Blob([response], { type: 'application/pdf' });
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
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
    this.modalRef.shown.subscribe(() => {
      setTimeout(() => {
        this.takeScreenShot();
      }, 1000);
    });
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
      console.log(districtId.substring(0, 4));
      console.log(districtId);
      this.agreementService
        .searchDistricts(districtId.substring(0, 4))
        .subscribe((response) => {
          console.log(response);
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
  generatePDF() {
    // this.takeScreenShot();
    var d = new Date();
    const month = d.toLocaleString('default', { month: 'long' });
    var archivo =
      'EECC_' +
      '_' +
      this.tiempoConCeros(d.getDate()) +
      '-' +
      this.tiempoConCeros(d.getMonth() + 1) +
      '-' +
      d.getFullYear();
    var fecha =
      this.tiempoConCeros(d.getDate()) +
      '/' +
      this.tiempoConCeros(d.getMonth() + 1) +
      '/' +
      d.getFullYear();
    var hora =
      this.tiempoConCeros(d.getHours()) +
      ':' +
      this.tiempoConCeros(d.getMinutes()) +
      ':' +
      this.tiempoConCeros(d.getSeconds());
    let docDefinition = {
      content: [
        { text: 'Acuerdo Detalle', style: 'header' },
        {
          style: 'tableExample',
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, false, false],
                  style: 'subtitle',
                  text: 'Datos generales',
                },
                {
                  border: [false, true, true, false],
                  text: '',
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Código',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Vigencia',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'ANP',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Departamento',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Estado',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Provincia',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Nombre',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Distrito',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Fecha de suscripción',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Localización',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, true],
                  columns: [
                    {
                      text: 'Objetivo general',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, true],
                  columns: [
                    {
                      text: 'Observaciones',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
            ],
          },
        },
        {
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, true, false],
                  style: 'subtitle',
                  text: 'Suscriptores',
                },
              ],
              [
                {
                  border: [true, false, true, true],
                  margin: [0, 0, 0, 10],
                  columns: [
                    {
                      text: [
                        {
                          text: 'suscriptor 1 \n',
                          decoration: 'underline',
                          lineHeight: 1.5,
                        },
                        { text: 'Area 1' },
                      ],
                      alignment: 'center',
                    },
                    {
                      text: [
                        {
                          text: 'suscriptor 2 \n',
                          decoration: 'underline',
                          lineHeight: 1.5,
                        },
                        { text: 'Area 2' },
                      ],
                      alignment: 'center',
                    },
                  ],
                },
              ],
            ],
          },
        },
        {
          style: 'tableExample',
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, true, false],
                  style: 'subtitle',
                  text: 'Compromisos',
                },
              ],
              [
                {
                  border: [true, false, true, true],
                  style: 'tableExample',
                  table: {
                    widths: '*',
                    headerRows: 1,
                    body: [
                      [
                        {
                          text: 'Compromiso',
                          style: 'tableHeader',
                          alignment: 'center',
                        },
                        {
                          text: 'Suscriptor',
                          style: 'tableHeader',
                          alignment: 'center',
                        },
                        {
                          text: 'Fecha registro',
                          style: 'tableHeader',
                          alignment: 'center',
                        },
                        {
                          text: 'Estado',
                          style: 'tableHeader',
                          alignment: 'center',
                        },
                      ],
                      [
                        'Sample value 1',
                        'Sample value 2',
                        'Sample value 3',
                        'Sample value 4',
                      ],
                      [
                        'Sample value 1',
                        'Sample value 2',
                        'Sample value 3',
                        'Sample value 4',
                      ],
                      [
                        'Sample value 1',
                        'Sample value 2',
                        'Sample value 3',
                        'Sample value 4',
                      ],
                      [
                        'Sample value 1',
                        'Sample value 2',
                        'Sample value 3',
                        'Sample value 4',
                      ],
                      [
                        'Sample value 1',
                        'Sample value 2',
                        'Sample value 3',
                        'Sample value 4',
                      ],
                    ],
                    alignment: 'center',
                    keepWithHeaderRows: 1,
                  },
                  layout: 'headerLineOnly',
                },
              ],
            ],
          },
        },
        {
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, false, false],
                  style: 'subtitle',
                  text: 'Beneficiarios',
                },
                {
                  border: [false, true, true, false],
                  text: '',
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Nro. familias',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Det. beneficiario',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Det. Benef. familia',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Beneficiarios indirectos',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, true, true],
                  colSpan: 2,
                  table: {
                    widths: '*',
                    body: [
                      [
                        {
                          border: [true, true, true, true],
                          columns: [
                            {
                              text: 'Hombres',
                              alignment: 'left',
                            },
                            {
                              text: '00001',
                              alignment: 'right',
                              bold: true,
                            },
                          ],
                        },
                        {
                          border: [true, true, true, true],
                          columns: [
                            {
                              text: 'Mujeres',
                              alignment: 'left',
                            },
                            {
                              text: '00001',
                              alignment: 'right',
                              bold: true,
                            },
                          ],
                        },
                        {
                          border: [true, true, true, true],
                          columns: [
                            {
                              text: 'Total',
                              alignment: 'left',
                            },
                            {
                              text: '00001',
                              alignment: 'right',
                              bold: true,
                            },
                          ],
                        },
                      ],
                    ],
                  },
                },
              ],
            ],
          },
        },
        {
          style: 'tableExample',
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, false, false],
                  style: 'subtitle',
                  text: 'Ámbito',
                },
                {
                  border: [false, true, true, false],
                  text: '',
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Ámbito total ADC',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Detalle ámbito bajo restauración',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Superficie intervención',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Nombre(s) del(os) sector(es) de V y C',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Ubic. superfice intervención',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Superficie del(os) sector(es) de V y C',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Detalle superficie intervención',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Detalle del(os) sector(es) de V y C',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, false],
                  columns: [
                    {
                      text: 'Superficie bajo restauración',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, false],
                  columns: [
                    {
                      text: 'Modalidad gestión del territorio ADC',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  border: [true, false, false, true],
                  columns: [
                    {
                      text: 'Superficie Ámbito de control',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
                {
                  border: [false, false, true, true],
                  columns: [
                    {
                      text: '',
                      alignment: 'left',
                    },
                    {
                      text: '',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
            ],
            keepWithHeaderRows: 7,
            headerRows: 7,
          },
        },
        {
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, false, false],
                  style: 'subtitle',
                  text: 'Articulaciones',
                },
                {
                  border: [false, true, true, false],
                  text: '',
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Plan maestro',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Plan de desarrollo local',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Plan de vida de las comunidades',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Plan de vida institucional ECA',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, true],
                  columns: [
                    {
                      text: 'Zonificación forestal',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
            ],
          },
        },
        {
          style: 'tableExample',
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [true, true, false, false],
                  style: 'subtitle',
                  text: 'Financiamiento',
                },
                {
                  border: [false, true, true, false],
                  text: '',
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Se ha apalancado financiamiento',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'financiamiento en soles',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Fuente de financiamiento',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, false],
                  columns: [
                    {
                      text: 'Modalidad de financiamiento',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [true, false, true, true],
                  columns: [
                    {
                      text: 'Nombre del fondo',
                      alignment: 'left',
                    },
                    {
                      text: '00001',
                      alignment: 'right',
                      bold: true,
                    },
                  ],
                },
              ],
            ],
          },
        },
        {
          table: {
            widths: '*',
            body: [
              [
                {
                  border: [false, false, false, false],
                  style: 'subtitle',
                  text: 'Mapa',
                },
                {
                  border: [false, false, false, false],
                  text: '',
                },
              ],
              [
                {
                  colSpan: 2,
                  border: [false, false, false, false],
                  columns: [
                    {
                      image: this.photoUrl,

                      width: 550,
                    },
                  ],
                },
              ],
            ],
            keepWithHeaderRows: 2,
            headerRows: 2,
          },
        },
      ],
      styles: {
        header: {
          fontSize: 18,
          bold: true,
          alignment: 'center',
        },
        tableExample: {
          margin: [0, 15, 0, 15],
        },
        subtitle: {
          fontSize: 12,
          bold: true,
          alignment: 'left',
        },
        tableSuscriptor: {},
        tableHeader: {
          bold: true,
          fontSize: 13,
          color: 'black',
        },
      },
      pageMargins: [20, 60, 20, 40],
      footer: function (currentPage, pageCount, pageSize) {
        return {
          columns: [
            'SIMRAC',
            {
              alignment: 'right',
              text: [
                { text: currentPage.toString(), italics: true },
                ' de ',
                { text: currentPage.toString(), italics: true },
              ],
            },
          ],
          margin: [40, 10, 20, 40],
        };
      },
      header: function () {
        return {
          columns: [
            {
              alignment: 'left',
              image:
                'data:image/png;base64,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',
            },
            {
              alignment: 'right',
              text: [
                { text: 'Fecha de Emisión: ' + fecha },
                {
                  text: '\n Hora de Emisión: ' + hora,
                } /*, { text: '\n Usuario: ' + usuario }*/,
              ],
            },
          ],
          margin: [40, 10, 20, 40],
        };
      },
    };

    pdfMake
      .createPdf(docDefinition)
      .download(
        'SIMRAC_' +
          '_' +
          this.tiempoConCeros(d.getDate()) +
          '-' +
          this.tiempoConCeros(d.getMonth() + 1) +
          '-' +
          d.getFullYear()
      );
  }
  tiempoConCeros(tiempo) {
    return (tiempo < 10 ? '0' : '') + tiempo;
  }
  takeScreenShot() {
    this.view.takeScreenshot().then((screenShot) => {
      this.photoUrl = screenShot.dataUrl;
    });
  }
}
