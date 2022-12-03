import {Component, OnInit} from '@angular/core';
import {rideRequestService} from "../../service/rideRequest.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {RideRequest} from "../../model/RideRequest";
import {User} from "../../model/User";
import {Address} from "../../model/Address";

@Component({
  selector: 'app-create-update-ride-request',
  templateUrl: './create-update-ride-request.component.html',
  styleUrls: ['./create-update-ride-request.component.css']
})
export class CreateUpdateRideRequestComponent implements OnInit {

  isLoggedIn: boolean = false;
  isOwner: boolean = false;
  rideRequestFromStorage?: RideRequest
  userString: string | null = localStorage.getItem("user");
  user?: User;


  constructor(private rideRequestService: rideRequestService,
              private router: Router,
              private location: Location) {
  }


  ngOnInit(): void {
    let rideRequestFromStore: string | null = localStorage.getItem('selected-ride-request');
    if (rideRequestFromStore) {
      this.rideRequestFromStorage = JSON.parse(rideRequestFromStore);
      // @ts-ignore
      this.location.replaceState('/ride-request/' + this.rideRequestFromStorage?.id)
    }
    if (this.userString) {
      this.user = JSON.parse(this.userString);
      this.isLoggedIn = true;
      if (this.user?.id == this.rideRequestFromStorage?.user.id) {
        this.isOwner = true;
      }
    }
    if(rideRequestFromStore && !this.isLoggedIn) {
      this.router.navigate(['/login'])
    }
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
      let rideRequestToUpdate: RideRequest = {
        id: data.id,
        title: data.title,
        description: data.description,
        user: this.user,
        fromAddress: fromAddress,
        toAddress: toAddress
      }

      this.rideRequestService.updateRequest(rideRequestToUpdate).subscribe(() => {
          console.log(`ride request with id ${rideRequestToUpdate.id} updated succesfully`);
          this.router.navigate(['/overview'])
        }
      )
    } else {
      console.error("please login to update request");
      this.router.navigate(['/overview']);
    }
  }

  onClickSubmitCreate(data: any) {
    if (this.user && this.isLoggedIn) {
      let fromAddress: Address = {
        id: 0,
        street: data.new_from_street,
        houseNumber: data.new_from_house_number,
        postalCode: data.new_from_postal_code,
        location: data.new_from_location
      }
      let toAddress: Address = {
        id: 0,
        street: data.new_to_street,
        houseNumber: data.new_to_house_number,
        postalCode: data.new_to_postal_code,
        location: data.new_to_location
      }
      let rideRequestToCreate: RideRequest = {
        id: data.id,
        title: data.title,
        description: data.description,
        user: this.user,
        fromAddress: fromAddress,
        toAddress: toAddress
      }
      console.log(rideRequestToCreate);
      this.rideRequestService.createRequest(rideRequestToCreate).subscribe(() => {
        console.log(`Create ride request successfull`)
        localStorage.setItem("selected-ride-request", JSON.stringify(rideRequestToCreate))
        this.router.navigate(['/overview'])
      })
    } else {
      console.error("please login with the correct account to create request");
      this.router.navigate(['/overview']);
    }
  }

  clickOnDeleteButton(rideRequest: RideRequest) {
    this.rideRequestService.deleteRequest(rideRequest).subscribe(() => {
      console.log(`ride request with id ${rideRequest.id} deleted succesfully`)
      this.router.navigate(['/overview'])
    })
  }

  clickOnCancelButton() {
    this.router.navigate(['/overview'])
  }
}
