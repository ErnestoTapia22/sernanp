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
      const contentDataURL = canvas.toDataURL('image/jpeg', 2.0);
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
  sendToPdf2() {}
  makePDF(content: HTMLElement) {
    var quotes = content;
    html2canvas(quotes, { scale: 2 }).then((canvas) => {
      //! MAKE YOUR PDF
      var doc = new jspdf('p', 'mm', 'a4');

      var imgData = canvas.toDataURL('image/png', 1.0);
      var pageHeight = doc.internal.pageSize.getHeight();
      var pageWidth = doc.internal.pageSize.getWidth();

      var imgheight = (content.clientHeight * 25.4) / 96; //px to mm
      var pagecount = Math.ceil(imgheight / pageHeight);

      /* add initial page */
      doc.addPage('a4', 'p');
      doc.addImage(imgData, 'PNG', 2, 0, pageWidth - 4, 0);

      let image = new Image();
      image.src = './assets/images/logo-sernanp.png';
      image.width = 150;

      /* add extra pages if the div size is larger than a a4 size */
      if (pagecount > 0) {
        var j = 1;
        while (j != pagecount) {
          doc.addPage('a4', 'p');
          doc.addImage(image, 'PNG', 2, -(j * pageHeight), pageWidth - 4, 0);
          doc.addImage(
            imgData,
            'PNG',
            2,
            -(j * pageHeight + 5),
            pageWidth - 4,
            0
          );
          j++;
        }
      }
      doc.deletePage(1);
      //! after the for loop is finished running, we save the pdf.
      doc.save('Test.pdf');
    });
  }
}
