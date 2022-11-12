import {Component, OnDestroy, OnInit} from '@angular/core';
import {RideOffer} from "../../model/RideOffer";
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {User} from "../../model/User";

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
  }

  ngOnDestroy(): void {
    localStorage.removeItem("selected-ride-offer");
  }


  onClickSubmitUpdate(data: any) {
    if (this.user && this.isOwner) {
      let rideOfferToUpdate: RideOffer = {
        id: data.id,
        title: data.title,
        description: data.description,
        user: this.user
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
      let rideOfferToCreate: RideOffer = {
        id: 0,
        title: data.newTitle,
        description: data.newDescription,
        user: this.user
      }
      console.log(rideOfferToCreate);
      this.rideOfferService.createOffer(rideOfferToCreate).subscribe(() => {
        console.log(`Create ride offer successfull`)
        this.router.navigate(['/overview'])
      })
    } else {
      console.error("please login to update offer")
      this.router.navigate(['/overview']);
    }
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
