import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  constructor(private agreementService: AgreementService) {}

  ngOnInit(): void {}
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
}
