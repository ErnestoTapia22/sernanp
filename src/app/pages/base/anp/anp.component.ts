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
        code: 'ANP-20218976',
        name: 'sfsfsd',
        field: 'Campo',
      },
      {
        code: 'ANP-20218976',
        name: 'sfsfsd',
        field: 'Campo',
      },
      {
        code: 'ANP-20218976',
        name: 'sfsfsd',
        field: 'Campo',
      },
      {
        code: 'ANP-20218976',
        name: 'sfsfsd',
        field: 'Campo',
      },
      {
        code: 'ANP-20218976',
        name: 'sfsfsd',
        field: 'Campo',
      },
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
