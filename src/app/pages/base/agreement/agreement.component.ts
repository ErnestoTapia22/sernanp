import { Component, OnInit } from '@angular/core';
import { AgreementService } from '../../../_services/base/agreement.service';
import { environment } from 'src/environments/environment';
var shp = require('shpjs');
// var shapefile = require('shapefile');

@Component({
  selector: 'app-agreement',
  templateUrl: './agreement.component.html',
  styleUrls: ['./agreement.component.css'],
})
export class AgreementComponent implements OnInit {
  constructor(private agreementService: AgreementService) {}

  ngOnInit(): void {}
  async readURL(event) {
    let thiss = this;
    // this.validateSize = false;
    if (event.target.files[0] && event.target.files) {
      var file: File = event.target.files[0];
      console.log(file);
      // const blob = new Blob(file.arrayBuffer);
      // var bufferPromise = blob.arrayBuffer();

      // blob.arrayBuffer().then(buffer => /* process the ArrayBuffer */);

      // var buffer = await blob.arrayBuffer();
      // console.log(file.size);
      // let shape = new FileReader();
      // const fileContentStream = await file.stream();
      // const fileSliceBlob = file.slice(0, file.length);
      // const fileSliceBlobStream = await fileSliceBlob.stream();
      // shape.onloadend = function (e) {
      // const result = await this.readFile(file);

      thiss.convertToGeoJson2(file.stream);
      // };

      // shape.readAsArrayBuffer(file);

      // try {
      //   this.agreementService.uploadShape(file).subscribe((data) => {
      //     console.log(data);
      //   });
      // } catch (error) {
      //   console.log(error);
      // }

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
  // convertToGeoJson(file) {
  //   shapefile
  //     .open(file)
  //     .then((source) =>
  //       source.read().then(function log(result) {
  //         if (result.done) return;
  //         console.log(result.value);
  //         return source.read().then(log);
  //       })
  //     )
  //     .catch((error) => console.error(error.stack));
  // }
  async convertToGeoJson2(file) {
    // debugger;
    const geojson = await shp(file);
    console.log(geojson);
    // shp(file).then(function (data) {
    //   //do stuff with data
    //   console.log(data);
    // });

    //   shapefile
    //     .open(file)
    //     .then((source) =>
    //       source.read().then(function log(result) {
    //         if (result.done) return;
    //         console.log(result.value);
    //         return source.read().then(log);
    //       })
    //     )
    //     .catch((error) => console.error(error.stack));
  }
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
