import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup } from '@angular/forms';
import { AnpService } from '../../../_services/base/anp.service';

@Component({
  selector: 'app-anp',
  templateUrl: './anp.component.html',
  styleUrls: ['./anp.component.css'],
})
export class AnpComponent implements OnInit {
  pageSize: Number = 0;
  page: Number = 0;
  total: Number = 0;
  anpList: any[];
  form: FormGroup;
  isLoading: Boolean = false;
  constructor(private anpService: AnpService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.buildForm();
    this.anpList = [
      {
        index: '1',
        code: 'SN06',
        name: 'Megantoni',
        department: 'Cusco',
        category: "Santuario Nacional"
      },
      {
        index: '2',
        code: 'RC03',
        name: 'Amarakaeri',
        department: 'Madre de Dios',        
        category: 'Reserva Comunal'
      },
      {
        index: '3',
        code: 'RVS03',
        name: 'Bosques Nublados de Udima',
        department: 'Cajamarca',        
        category: 'Refugio de Vida Silvestre'
      },
      {
        index: '4',
        code: 'RVS01',
        name: 'Laquipampa',
        department: 'Lambayeque',        
        category: 'Refugio de Vida Silvestre'
      },
      {
        index: '5',
        code: 'RVS02',
        name: 'Los Pantanos de Villa',
        department: 'Lima',        
        category: 'Refugio de Vida Silvestre'
      }
    ];
    // this.listAnp();
  }
  getPage(e) {}
  onChangePageSize(e) {}
  listAnp() {
    this.anpService.agreementList().subscribe((response) => {
      this.anpList = response;
    });
  }
  search() {}
  buildForm() {
    this.form = this.fb.group({
      code: [''],
      name: [''],
      pageSize: [5],
    });
  }
  get f() {
    return this.form.controls;
  }
}
