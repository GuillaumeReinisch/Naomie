import { TestBed, inject } from '@angular/core/testing';

import { ParametersService } from './parameters.service';

describe('ParametersService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ParametersService]
    });
  });

  it('should ...', inject([ParametersService], (service: ParametersService) => {
    expect(service).toBeTruthy();
  }));
});
