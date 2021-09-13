import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnpComponent } from './anp.component';

describe('AnpComponent', () => {
  let component: AnpComponent;
  let fixture: ComponentFixture<AnpComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnpComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AnpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
