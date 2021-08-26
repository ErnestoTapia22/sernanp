import { TestBed } from '@angular/core/testing';

import { AnpService } from './anp.service';

describe('AnpService', () => {
  let service: AnpService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnpService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
