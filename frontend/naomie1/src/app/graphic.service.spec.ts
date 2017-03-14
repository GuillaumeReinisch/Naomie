import { TestBed, inject } from '@angular/core/testing';

import { GraphicService } from './graphic.service';

describe('GraphicService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GraphicService]
    });
  });

  it('should ...', inject([GraphicService], (service: GraphicService) => {
    expect(service).toBeTruthy();
  }));
});
