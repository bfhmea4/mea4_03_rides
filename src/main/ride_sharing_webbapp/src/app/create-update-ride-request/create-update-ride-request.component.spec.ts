import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateRideRequestComponent } from './create-update-ride-request.component';

describe('CreateUpdateRideRequestComponent', () => {
  let component: CreateUpdateRideRequestComponent;
  let fixture: ComponentFixture<CreateUpdateRideRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUpdateRideRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUpdateRideRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
