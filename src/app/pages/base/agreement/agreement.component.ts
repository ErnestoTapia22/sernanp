import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  selectedAnp: number = 0;
  anp: Object[] = [];
  constructor(private agreementService: AgreementService) {}

  ngOnInit(): void {
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
}
