import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-master-plan',
  templateUrl: './master-plan.component.html',
  styleUrls: ['./master-plan.component.css'],
})
export class MasterPlanComponent implements OnInit {
  form: FormGroup;
  commitments: any[];
  isLoading: Boolean = false;
  constructor(private fb: FormBuilder) {}
  get f() {
    return this.form.controls;
  }

  ngOnInit(): void {
    this.buildForm();
    this.commitments = [
      {
        index: '1',
        code: 'OB 1',
        description: 'Mantener la cobertura vegetal del Parque Nacional',
        component: 'Ambiental'
      },
      {
        index: '2',
        code: 'OB 2',
        description: 'Mantener poblaciones de especies de interés para la gestion del Parque Nacional',    
        component: 'Ambiental'
      }
    ];
  }
  buildForm() {
    this.form = this.fb.group({
      code: [''],
      name: [''],
    });
  }
}
