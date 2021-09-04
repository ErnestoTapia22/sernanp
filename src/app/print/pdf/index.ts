import { jsPDF } from 'jspdf';
export default (printData) => {
  const doc = new jsPDF({
    orientation: 'p',
    unit: 'pt',
  });
  const pageWidth = doc.internal.pageSize.width;
  const pageCenter = pageWidth / 2;
  const marginRight = 40;
  //prettier-ignore
  const marginLeft = pageWidth - marginRight;
  const padding = 10;
  const titleLineHeight = 30;
  doc.setFont('helvetica', 'normal');
  //image
  doc.addImage(
    './assets/print/logo-sernanp.png',
    'PNG',
    marginRight,
    15,
    180,
    50
  );

  //title
  doc.setFontSize(22);
  doc.text(
    'Resumen acuerdo',
    pageCenter - doc.getTextWidth('Resumen acuerdo') / 2,
    90
  );
  //title underline
  doc.setLineWidth(2);
  doc.line(
    pageCenter - doc.getTextWidth('Resumen acuerdo') / 2,
    92,
    pageCenter -
      doc.getTextWidth('Resumen acuerdo') / 2 +
      doc.getTextWidth('Resumen acuerdo'),
    92
  );
  //first box
  doc.setLineWidth(0.5);
  doc.line(marginRight, 105, marginLeft, 105);

  doc.line(marginLeft, 105, marginLeft, 300);
  doc.line(marginRight, 105, marginRight, 300);

  doc.line(marginRight, 300, marginLeft, 300);

  //general data
  doc.setFontSize(17);
  doc.text('General', marginRight + padding, 125);

  doc.setFontSize(12);
  doc.text('ANP', marginRight + padding, 155);

  doc.text('Estado', marginRight + padding, 175);
  doc.text('Fecha de suscripción', marginRight + padding, 195);
  doc.text('Vigencia en años', marginRight + padding, 215);
  doc.text('Observaciones', marginRight + padding, 235);
  doc.text('Objetivo general', pageCenter + padding, 155);

  doc.setFont('helvetica', 'bold');
  doc.text(
    printData.item.anp || '<ANP>',
    pageCenter - doc.getTextWidth(printData.item.anp || '<anp>') - padding,
    155
  );
  doc.text(
    printData.item.agreementState.stateName || '<state>',
    pageCenter -
      doc.getTextWidth(printData.item.agreementState.stateName || '<state>') -
      padding,
    175
  );
  doc.text(
    printData.item.firm.toString() || '<suscription>',
    pageCenter -
      doc.getTextWidth(printData.item.firm.toString() || '<suscription>') -
      padding,
    195
  );
  doc.text(
    printData.item.vigency.toString() || '<valid>',
    pageCenter -
      doc.getTextWidth(printData.item.vigency.toString() || '<valid>') -
      padding,
    215
  );
  doc.text(
    printData.item.observation || '<observation>',
    pageCenter -
      doc.getTextWidth(printData.item.observation || '<observation>') -
      padding,
    235
  );
  doc.text(
    printData.item.goal || '<goal>',
    marginLeft - doc.getTextWidth(printData.item.goal || '<goal>') - padding,
    155
  );

  //second box
  doc.setLineWidth(2);
  doc.line(marginRight, 310, marginLeft, 310);

  //   doc.line(marginLeft, 310, marginLeft, 400);
  //   doc.line(marginRight, 310, marginRight, 400);

  doc.line(marginRight, 400, marginLeft, 400);
  doc.setFontSize(17);
  doc.setFont('helvetica', 'normal');
  doc.text('Suscriptores', marginRight, 330);

  //   doc.text()

  doc.save(`report_${printData.item.code}.pdf`);
};
