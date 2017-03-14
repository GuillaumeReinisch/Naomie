import { TestBed, inject } from '@angular/core/testing';

import { NumericalDataService } from './numerical-data.service';

describe('NumericalDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NumericalDataService]
    });
  });

  it('should ...', inject([NumericalDataService], (service: NumericalDataService) => {
    expect(service).toBeTruthy();
  }));
});
