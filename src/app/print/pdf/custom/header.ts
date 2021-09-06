import { jsPDF } from 'jspdf';
export default (doc: jsPDF, data, startY, fontSizes, lineSpacing) => {
  const reportNrTxt = data.label.reportNumber;
  let startX = 57;
  const pageWidth = doc.internal.pageSize.width;
  const endX = pageWidth - startX;
  doc.setFont('normal');
  doc.setFontSize(fontSizes.SubTitleFontSize);
  startY = 243;
  doc.text(reportNrTxt, startX, startY);
  doc.setFont('normal', 'normal', 'bold');

  startX += doc.getStringUnitWidth(reportNrTxt) * fontSizes.SubTitleFontSize;
  doc.text(data.invoice.number, startX, startY);

  doc.setFont('normal');
  const location = data.invoice.location ? data.invoice.location + ', ' : '';
  doc.text(location + data.invoice.date, endX, startY);

  startY += lineSpacing * 2;
  startX = 57;

  doc.setFontSize(fontSizes.TitleFontSize);
  doc.text(data.label.invoice, startX, (startY += lineSpacing + 2));
  doc.text(data.invoice.subject, startX, (startY += lineSpacing * 2));

  doc.setDrawColor(157, 183, 128);
  doc.setLineWidth(0.5);
  startY += lineSpacing;
  doc.line(startX, startY, endX, startY);

  return startY;
};
