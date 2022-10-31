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
  rideRequestsLoaded: boolean = false;
  rideOffersLoaded: boolean = false;
  rideRequestsBtnClicked: boolean = false;
  rideOffersBtnClicked: boolean = true;


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
    this.getAllOffers();
    this.rideOffersBtnClicked = true;
    this.rideRequestsBtnClicked = false;
  }

  requestsBtnClicked() {
    this.getAllRequests();
    this.rideRequestsBtnClicked = true;
    this.rideOffersBtnClicked = false;
  }

  getAllOffers() {
    //TODO check localStorage for list

    // if(!localStorage.getItem("token")) {
    //   this.router.navigate(['/login']);
    // }
    // if (!this.rideOffersLoaded) {
    //   this.rideOffers = localStorage.getItem("rideOffers")
    // } else {
    console.log("sending GET ALL OFFERS request...");
    this.rideOffers = [];
    this.rideOfferService.getAllOffers().subscribe(offers => {
      // localStorage.setItem("rideOffers", offers)
      //
      this.rideOffers = <RideOffer[]>offers;
      // this.rideOffersLoaded = true;
    })
    // }
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
}
