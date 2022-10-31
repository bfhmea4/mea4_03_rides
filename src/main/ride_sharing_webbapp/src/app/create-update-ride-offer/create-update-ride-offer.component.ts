import {Component, OnDestroy, OnInit} from '@angular/core';
import {RideOffer} from "../../model/RideOffer";
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import { Location } from "@angular/common";

@Component({
  selector: 'app-create-update-ride-offer',
  templateUrl: './create-update-ride-offer.component.html',
  styleUrls: ['./create-update-ride-offer.component.css']
})
export class CreateUpdateRideOfferComponent implements OnInit, OnDestroy {

  rideOfferFromStorage?: RideOffer;

  constructor(private rideOfferService: rideOfferService,
              private router: Router,
              private location: Location) {

  }

  ngOnInit(): void {
    let rideOfferFromStore: string | null = localStorage.getItem('selected-ride-offer');
    if(rideOfferFromStore) {
      this.rideOfferFromStorage = JSON.parse(rideOfferFromStore);
      // @ts-ignore
      this.location.replaceState('/ride-offer/' + this.rideOfferFromStorage?.id)

    }
  }

  ngOnDestroy(): void {
    localStorage.setItem("selected-ride-offer", "");
  }



  onClickSubmitUpdate(data: any) {
    let rideOfferToUpdate : RideOffer = {
      id: data.id,
      title: data.title,
      description: data.description
    }
    this.rideOfferService.updateOffer(rideOfferToUpdate).subscribe (() => {
      console.log(`ride offer with id ${rideOfferToUpdate.id} updated succesfully`);
      this.router.navigate(['/overview'])
    })
  }

  onClickSubmitCreate(data: any) {
    let rideOfferToCreate : RideOffer = {
      id: 0,
      title: data.newTitle,
      description: data.newDescription
    }
    console.log(rideOfferToCreate);
    this.rideOfferService.createOffer(rideOfferToCreate).subscribe(() => {
      console.log(`Create ride offer successfull`)
      this.router.navigate(['/overview'])
    })

  }

  clickOnDeleteButton(id: number) {
    this.rideOfferService.deleteOffer(id).subscribe(() => {
      console.log(`ride offer with id ${id} deleted succesfully`)
      this.router.navigate(['/overview'])
    })

  }

  clickOnCancelButton() {
    this.router.navigate(['/overview'])
  }


}
