import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-master-plan',
  templateUrl: './master-plan.component.html',
  styleUrls: ['./master-plan.component.css'],
})
export class MasterPlanComponent implements OnInit {
  form: FormGroup;
  constructor(private fb: FormBuilder) {}
  get f() {
    return this.form.controls;
  }

  ngOnInit(): void {
    this.buildForm();
  }
  buildForm() {
    this.form = this.fb.group({
      code: [''],
      name: [''],
    });
  }
}
