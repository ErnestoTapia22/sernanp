import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-agreement-detail',
  templateUrl: './agreement-detail.component.html',
  styleUrls: ['./agreement-detail.component.css'],
})
export class AgreementDetailComponent implements OnInit {
  form: FormGroup;
  fieldArray: Array<any> = [];
  mapProperties: any;
  mapViewProperties: any;
  anp: Object[] = [];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.mapProperties = {
      basemap: 'hybrid',
      ground: 'world-elevation',
    };
    this.mapViewProperties = {
      center: [-75.744, -8.9212],
      zoom: 9,
    };
    this.buildForm();
  }
  onMapInit({ map, view }) {}
  buildForm() {
    this.form = this.fb.group({
      code: new FormControl(),
      anp: new FormControl(),
      goal: new FormControl(),
    });
  }
}
