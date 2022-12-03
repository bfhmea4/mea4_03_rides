import {Component, OnDestroy, OnInit} from '@angular/core';
import {RideOffer} from "../../model/RideOffer";
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {User} from "../../model/User";
import {Address} from "../../model/Address";

@Component({
  selector: 'app-create-update-ride-offer',
  templateUrl: './create-update-ride-offer.component.html',
  styleUrls: ['./create-update-ride-offer.component.css']
})
export class CreateUpdateRideOfferComponent implements OnInit, OnDestroy {

  isLoggedIn: boolean = false;
  isOwner: boolean = false;
  rideOfferFromStorage?: RideOffer;
  userString: string|null = localStorage.getItem("user");
  user?: User;

  constructor(private rideOfferService: rideOfferService,
              private router: Router,
              private location: Location) {

  }

  ngOnInit(): void {
    let rideOfferFromStore: string | null = localStorage.getItem('selected-ride-offer');
    if (rideOfferFromStore) {
      this.rideOfferFromStorage = JSON.parse(rideOfferFromStore);
      // @ts-ignore
      this.location.replaceState('/ride-offer/' + this.rideOfferFromStorage?.id)
    }
    if(this.userString) {
      this.user = JSON.parse(this.userString);
      this.isLoggedIn = true;
      if (this.user?.id == this.rideOfferFromStorage?.user.id) {
        this.isOwner = true;
      }
    }
    if(rideOfferFromStore && !this.isLoggedIn) {
      this.router.navigate(['/login'])
    }
  }

  ngOnDestroy(): void {
    localStorage.removeItem("selected-ride-offer");
  }


  onClickSubmitUpdate(data: any) {
    if (this.user && this.isOwner) {
      let fromAddress: Address = {
        id: data.from_id,
        street: data.from_street,
        houseNumber: data.from_house_number,
        postalCode: data.from_postal_code,
        location: data.from_location
      }
      let toAddress: Address = {
        id: data.to_id,
        street: data.to_street,
        houseNumber: data.to_house_number,
        postalCode: data.to_postal_code,
        location: data.to_location
      }
      let rideOfferToUpdate: RideOffer = {
        id: data.id,
        title: data.title,
        description: data.description,
        user: this.user,
        fromAddress: fromAddress,
        toAddress: toAddress
      }
      this.rideOfferService.updateOffer(rideOfferToUpdate).subscribe(() => {
        console.log(`ride offer with id ${rideOfferToUpdate.id} updated succesfully`);
        this.router.navigate(['/overview'])
      })
    } else {
      console.error("please login to update offer")
      this.router.navigate(['/overview'])
    }
  }

  onClickSubmitCreate(data: any) {
    if (this.user && this.isLoggedIn) {
      let from: Address = {
        id: 0,
        street: data.new_from_street,
        houseNumber: data.new_from_house_number,
        postalCode: data.new_from_postal_code,
        location: data.new_from_location
      }
      console.log('from: ' + from.street + from.location + from.postalCode);
      let to: Address = {
        id: 0,
        street: data.new_to_street,
        houseNumber: data.new_to_house_number,
        postalCode: data.new_to_postal_code,
        location: data.new_to_location
      }
      console.log('to: ' + to.street + to.location + to.postalCode);
      let rideOfferToCreate: RideOffer = {
        id: 0,
        title: data.newTitle,
        description: data.newDescription,
        user: this.user,
        fromAddress: from,
        toAddress: to
      }
      console.log(rideOfferToCreate);
      this.rideOfferService.createOffer(rideOfferToCreate).subscribe(() => {
        console.log(`Create ride offer successfull`)
        this.router.navigate(['/overview'])
      })
    } else {
      console.error("please login to create offer")
      this.router.navigate(['/overview']);
    }
  }

  clickOnDeleteButton(rideOffer: RideOffer) {
    this.rideOfferService.deleteOffer(rideOffer).subscribe(() => {
      console.log(`ride offer with id ${rideOffer.id} deleted succesfully`)
      this.router.navigate(['/overview'])
    })

  }

  clickOnCancelButton() {
    this.router.navigate(['/overview'])
  }


}
