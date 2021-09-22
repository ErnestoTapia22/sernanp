import { Injectable } from '@angular/core';
import html2canvas from 'html2canvas';
import jspdf from 'jspdf';
import domtoimage from 'dom-to-image';
@Injectable({
  providedIn: 'root',
})
export class PdfService {
  PDF_EXTENSION = '.pdf';

  constructor() {}
  sendToPdf(fileName: string, documentId: string, content: HTMLElement) {
    html2canvas(content, { scale: 4 }).then((canvas) => {
      const contentDataURL = canvas.toDataURL('image/jpeg', 1.0);
      let pdf = new jspdf('p', 'mm', 'a4');

      // let pdf = new jspdf('p', 'cm', 'a4'); //Generates PDF in landscape mode
      // let pdf = new jspdf('p', 'cm', 'a4'); //Generates PDF in portrait mode
      pdf.addImage(contentDataURL, 'PNG', 0, 0, 210, 297);
      pdf.setProperties({
        title: 'This is my title',
      });
      window.open(URL.createObjectURL(pdf.output('blob')));
    });
    // html2canvas(content, { scale: 4 }).then((canvas) => {
    //   const contentDataURL = canvas.toDataURL('image/jpeg', 1.0);
    //   let pdf = new jspdf('p', 'mm', 'a4');

    //   // let pdf = new jspdf('p', 'cm', 'a4'); //Generates PDF in landscape mode
    //   // let pdf = new jspdf('p', 'cm', 'a4'); //Generates PDF in portrait mode
    //   pdf.addImage(contentDataURL, 'PNG', 0, 0, 210, 297);
    //   pdf.setProperties({
    //     title: 'This is my title',
    //   });
    //   window.open(URL.createObjectURL(pdf.output('blob')));
    // });
  }
}
