import {Component, OnInit} from '@angular/core';
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {RideOffer} from "../../model/RideOffer";
import {rideRequestService} from "../../service/rideRequest.service";
import {RideRequest} from "../../model/RideRequest";

@Component({
  selector: 'app-ride-offer',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  rideOffers: RideOffer[] = [];
  rideRequests: RideRequest[] = [];


  constructor(private rideOfferService: rideOfferService,
              private rideRequestService: rideRequestService,
              private router: Router) {

  }

  ngOnInit()
    :
    void {
    this.offersBtnClicked();
  }



  offersBtnClicked() {
    this.changeColorOffersBtnClicked();
    // @ts-ignore
    document.getElementById("requestsList").hidden = true;
    // @ts-ignore
    document.getElementById("offersList").hidden = false;
    this.getAllOffers();
  }

  requestsBtnClicked() {
    this.changeColorRequestsBtnClicked();
    // @ts-ignore
    document.getElementById("offersList").hidden = true;
    // @ts-ignore
    document.getElementById("requestsList").hidden = false;
    this.getAllRequests();
  }

  getAllOffers(): any {
    //TODO check localStorage for list

    // let list = document.getElementById("offers-list");
    // if (list) {
    //   list.innerHTML
    // }
    console.log("sending GET ALL OFFERS request...");
    this.rideOffers = [];
    this.rideOfferService.getAllOffers().subscribe(offers => {
      // if(!localStorage.getItem("token")) {
      //   this.router.navigate(['/login']);
      // }
      this.rideOffers = <RideOffer[]>offers;
    })

  }

  getAllRequests() {
    //TODO check localStorage for list

    console.log("sending GET ALL REQUESTS request...");
    this.rideRequests = [];
    this.rideRequestService.getAllRequests().subscribe(requests => {
      // if(!localStorage.getItem("token")) {
      //   this.router.navigate(['/login']);
      // }
      this.rideRequests = <RideRequest[]>requests;
    })

  }

  changeColorOffersBtnClicked() {
    // @ts-ignore
    document.getElementById("offersBtn").style.backgroundColor = 'white'
    // @ts-ignore
    document.getElementById("requestsBtn").style.backgroundColor = 'grey';
  }

  changeColorRequestsBtnClicked() {
    // @ts-ignore
    document.getElementById("offersBtn").style.backgroundColor = 'grey'
    // @ts-ignore
    document.getElementById("requestsBtn").style.backgroundColor = 'white';
  }
}
