import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
import * as XLSX2 from 'sheetjs-style';

@Injectable({
  providedIn: 'root',
})
export class ExcelService {
  EXCEL_TYPE =
    'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
  EXCEL_EXTENSION = '.xlsx';

  constructor() {}
  public exportAsExcelFile(
    json: any[],
    excelFileName: string,
    documentId: string
  ): void {
    let objectMaxLength = [];
    for (let i = 0; i < json.length; i++) {
      let value = <any>Object.values(json[i]);
      for (let j = 0; j < value.length; j++) {
        if (typeof value[j] == 'number') {
          objectMaxLength[j] = 10;
        } else if (typeof value[j] == 'boolean') {
          objectMaxLength[j] = 15;
        } else if (value[j] instanceof Date) {
          objectMaxLength[j] = 15;
        } else {
          objectMaxLength[j] =
            objectMaxLength[j] >= value[j].length
              ? objectMaxLength[j] + 5
              : value[j].length + 5;
        }
      }
    }
    var wscols = [
      { width: objectMaxLength[0], wpx: 100 }, // first column
      { width: objectMaxLength[1], wpx: 100 }, // second column
      { width: objectMaxLength[2], wpx: 100 }, //...
      { width: objectMaxLength[3], wpx: 100 },
      { width: objectMaxLength[4], wpx: 100 },
      { width: objectMaxLength[5], wpx: 100 },
      { width: objectMaxLength[6], wpx: 100 },
      { width: objectMaxLength[7], wpx: 100 },
      { width: objectMaxLength[8], wpx: 100 },
      { width: objectMaxLength[9], wpx: 100 },
    ];
    const worksheet: XLSX2.WorkSheet = XLSX2.utils.json_to_sheet(json, {
      dateNF: 'dd/MM/yyyy',
    });
    worksheet['!cols'] = wscols;
    worksheet.C1.v = 'fecha de registro';
    worksheet.D1.v = 'estado';
    worksheet.E1.v = 'compromiso';
    worksheet.F1.v = 'suscriptor';
    worksheet.A1.v = 'activo';

    var range = XLSX2.utils.decode_range(worksheet['!ref']);
    for (var C = range.s.c; C <= range.e.c; ++C) {
      var address = XLSX2.utils.encode_col(C) + '1'; // <-- first row, column number C
      if (!worksheet[address]) continue;
      worksheet[address].v = worksheet[address].v.toUpperCase();
      worksheet[address].s = {
        // set the style for target cell
        font: {
          bold: true,
        },
      };
      worksheet[address].s = {
        fill: {
          fgColor: { rgb: '27272800' },
        },
        font: {
          color: { rgb: 'FFFFAA00' },
        },

        // set the style for target cell
        border: {
          bottom: { style: 'thin' },
          top: { style: 'thin' },
          left: { style: 'thin' },
          right: { style: 'thin' },
        },
      };
    }

    console.log('worksheet', worksheet);
    const workbook: XLSX2.WorkBook = {
      Sheets: { data: worksheet },
      SheetNames: ['data'],
    };

    const excelBuffer: any = XLSX2.write(workbook, {
      bookType: 'xlsx',
      type: 'array',
    });
    //const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'buffer' });
    this.saveAsExcelFile(excelBuffer, excelFileName, documentId);
  }
  private saveAsExcelFile(
    buffer: any,
    fileName: string,
    documentId: string
  ): void {
    const data: Blob = new Blob([buffer], {
      type: this.EXCEL_TYPE,
    });
    FileSaver.saveAs(
      data,
      fileName +
        '_' +
        documentId +
        '_' +
        new Date().getTime() +
        this.EXCEL_EXTENSION
    );
  }
}
