import { TestBed } from '@angular/core/testing';

import { MasterPlanService } from './master-plan.service';

describe('MasterPlanService', () => {
  let service: MasterPlanService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MasterPlanService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
