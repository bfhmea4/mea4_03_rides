import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateUpdateRideOfferComponent } from './create-update-ride-offer.component';

describe('CreateUpdateRideOfferComponent', () => {
  let component: CreateUpdateRideOfferComponent;
  let fixture: ComponentFixture<CreateUpdateRideOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateUpdateRideOfferComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateUpdateRideOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
