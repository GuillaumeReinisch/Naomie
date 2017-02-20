import { Injectable } from '@angular/core';

import {NumericalData,NUMERICALDATA}      from '../models/numerical-data'


@Injectable()
export class NumericalDataService {

  getNumericalData(): Promise<NumericalData> {
    return Promise.resolve(NUMERICALDATA);
  }
}
