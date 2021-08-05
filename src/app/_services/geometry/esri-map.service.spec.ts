import { TestBed } from '@angular/core/testing';

import { EsriMapService } from './esri-map.service';

describe('EsriMapService', () => {
  let service: EsriMapService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EsriMapService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
