import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParametersBrowserComponent } from './parameters-browser.component';

describe('ParametersBrowserComponent', () => {
  let component: ParametersBrowserComponent;
  let fixture: ComponentFixture<ParametersBrowserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParametersBrowserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParametersBrowserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
