import { Injectable } from '@angular/core';

import {Graphic,GRAPHS}      from '../models/data-mockup'


@Injectable()
export class GraphicService {

  getGraphics(): Promise<Graphic[]> {
    return Promise.resolve(GRAPHS);
  }
}
