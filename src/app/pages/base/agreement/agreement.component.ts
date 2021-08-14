import { Component, OnInit } from '@angular/core';
// import ogr2ogr from 'ogr2ogr';

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
  readURL(event): void {
    // this.validateSize = false;
    if (event.target.files[0] && event.target.files) {
      var file = event.target.files[0];
      console.log(file);
      console.log(file.size);
      // let shape = new FileReader();
      // shape.readAsBinaryString(file);

      // if (file.size < 50000) {

      //   const reader = new FileReader();
      //   reader.onload = e => this.imageSrc = reader.result;

      //   reader.readAsDataURL(file);
      //   this.spinner.show();
      //   this.datosPersonalesService.datosPersonalUploadPhoto(file, this.token.access_token)
      //     .subscribe(data => {
      //       this.updateUrlFoto(data);
      //     });
      // } else {
      //   this.validateSize = true;
      // }
    }

    // this.onRefreshPostulanteResumen();
  }
  async convertToGeoJson(file) {
    // let data = await ogr2ogr(file);
    // console.log(data);
  }
}
