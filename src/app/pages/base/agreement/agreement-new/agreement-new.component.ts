import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';
import { BehaviorSubject } from 'rxjs';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-agreement-new',
  templateUrl: './agreement-new.component.html',
  styleUrls: ['./agreement-new.component.css'],
})
export class AgreementNewComponent implements OnInit {
  selectedAnp: number = 0;
  obsQuery = new BehaviorSubject({ item: '' });
  anp: Object[] = [];
  form: FormGroup;
  mapProperties: any;
  mapViewProperties: any;
  fieldArray: Array<any> = [];
  newAttribute: any = {};
  constructor(
    private agreementService: AgreementService,
    private fb: FormBuilder
  ) {}

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
    let item = {
      name: '',
    };
    this.obsQuery.next({
      item: JSON.stringify(item),
    });
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
  onMapInit({ map, view }) {}
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
  addFieldValue() {
    this.fieldArray.push(this.newAttribute);
    this.newAttribute = {};
  }
  deleteFieldValue(index) {
    this.fieldArray.splice(index, 1);
  }
}
