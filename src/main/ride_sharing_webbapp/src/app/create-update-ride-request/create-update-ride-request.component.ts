import {Component, OnInit} from '@angular/core';
import {rideRequestService} from "../../service/rideRequest.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {RideRequest} from "../../model/RideRequest";

@Component({
  selector: 'app-create-update-ride-request',
  templateUrl: './create-update-ride-request.component.html',
  styleUrls: ['./create-update-ride-request.component.css']
})
export class CreateUpdateRideRequestComponent implements OnInit {

  rideRequestFromStorage?: RideRequest


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
  }


  onClickSubmitUpdate(data: any) {
    let rideRequestToUpdate: RideRequest = {
      id: data.id,
      content: data.content
    }
    this.rideRequestService.updateRequest(rideRequestToUpdate).subscribe(() => {
      console.log(`ride request with id ${rideRequestToUpdate.id} updated succesfully`);
      this.router.navigate(['/overview'])
    })
  }

  onClickSubmitCreate(data: any) {
    let rideRequestToCreate : RideRequest = {
      id: 0,
      content: data.content
    }
    console.log(rideRequestToCreate);
    this.rideRequestService.createRequest(rideRequestToCreate).subscribe(() => {
      console.log(`Create ride request successfull`)
      localStorage.setItem("selected-ride-request", JSON.stringify(rideRequestToCreate))
      this.router.navigate(['/overview'])
    })
  }

  clickOnDeleteButton(id: number) {
    this.rideRequestService.deleteRequest(id).subscribe(() => {
      console.log(`ride request with id ${id} deleted succesfully`)
      this.router.navigate(['/overview'])
    })
  }

  clickOnCancelButton() {
    this.router.navigate(['/overview'])
  }

}
