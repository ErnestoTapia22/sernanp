import { TestBed } from '@angular/core/testing';

import { ExcelAcService } from './excelAc.service';

describe('ExcelAcService', () => {
  let service: ExcelAcService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExcelAcService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
