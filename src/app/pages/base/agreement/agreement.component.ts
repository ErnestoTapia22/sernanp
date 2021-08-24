import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  selectedAnp: number = 0;
  obsQuery = new BehaviorSubject({ item: '' });
  anp: Object[] = [];
  form: FormGroup;

  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.buildForm();
    let item = {
      name: '',
    };
    this.obsQuery.next({
      item: JSON.stringify(item),
    });
    this.fillSelects();
  }
  async readURL(event) {}

  async readFile(file) {
    const arrayBuffer = await new Promise((resolve) => {
      const reader = new FileReader();
      // reader.onload = () => resolve(reader.result);

      reader.onload = () => resolve(reader.result);
      reader.readAsArrayBuffer(file);
    });

    return arrayBuffer;
  }
  get f() {
    return this.form.controls;
  }
  fillSelects() {
    try {
      if (
        environment.externalServices[0].agreement[0].url !== undefined &&
        environment.externalServices[0].agreement[0].url !== null
      ) {
        this.agreementService
          .getServices(`${environment.externalServices[0].agreement[0].url}`)
          .subscribe((response) => {
            console.log(response);
            if (response) {
              this.anp = response;
            }
          });
      }
    } catch (error) {}
  }
  buildForm() {
    this.form = this.fb.group({
      code: new FormControl(),
      anp: new FormControl(),
      goal: new FormControl(),
    });
  }
}
