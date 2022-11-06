import {Component, OnDestroy, OnInit} from '@angular/core';
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {RideOffer} from "../../model/RideOffer";
import {rideRequestService} from "../../service/rideRequest.service";
import {RideRequest} from "../../model/RideRequest";
import {User} from "../../model/User";

@Component({
  selector: 'app-ride-offer',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit, OnDestroy {

  rideOffers: RideOffer[] = [];
  rideRequests: RideRequest[] = [];
  rideRequestsBtnClicked: boolean = false;
  rideOffersBtnClicked: boolean = true;
  isLoggedIn: boolean = false;
  userString: string|null = localStorage.getItem("user");
  user?: User;


  constructor(private rideOfferService: rideOfferService,
              private rideRequestService: rideRequestService,
              private router: Router) {

  }

  ngOnInit(): void {
    if (localStorage.getItem("selected-ride-request")) {
      this.requestsBtnClicked()
    } else {
      this.offersBtnClicked()
    }
    localStorage.removeItem("selected-ride-request")
    localStorage.removeItem("selected-ride-offer")
    if(this.userString) {
      this.user = JSON.parse(this.userString);
      this.isLoggedIn = true;
    }
  }

  ngOnDestroy(): void {
    localStorage.removeItem("ride-offers");
    localStorage.removeItem("ride-requests");
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
    let offersListStorage: string | null = localStorage.getItem("ride-offers")
    if (offersListStorage) {
      this.rideOffers = JSON.parse(offersListStorage);
      console.log("Get all offer via storage.....")
    } else {
      console.log("sending GET ALL OFFERS request...");
      this.rideOffers = [];
      this.rideOfferService.getAllOffers().subscribe(offers => {
        this.rideOffers = <RideOffer[]>offers;
        localStorage.setItem("ride-offers", JSON.stringify(this.rideOffers));
        console.log(localStorage.getItem("ride-offers"));
      })
    }
  }

  getAllRequests() {
    let requestsListStorage: string | null = localStorage.getItem("ride-requests")
    if (requestsListStorage) {
      this.rideRequests = JSON.parse(requestsListStorage);
      console.log("Get all requests via storage.....")
    } else {
      console.log("sending GET ALL REQUESTS request...");
      this.rideRequests = [];
      this.rideRequestService.getAllRequests().subscribe(requests => {
        this.rideRequests = <RideRequest[]>requests;
        localStorage.setItem("ride-requests", JSON.stringify(this.rideRequests));
      })
    }
  }

  onClickOfferItem(rideOffer: RideOffer) {
    //TODO method without TS ignore
    localStorage.setItem("selected-ride-offer", JSON.stringify(rideOffer));
    console.log("navigate to update offer view");
    // @ts-ignore
    this.router.navigate(['/ride-offer'])
  }

  onClickRequestItem(rideRequest: RideRequest) {
    //TODO method without TS ignore
    localStorage.setItem("selected-ride-request", JSON.stringify(rideRequest));
    console.log("navigate to update request view");
    // @ts-ignore
    this.router.navigate(['/ride-request']);
    // this.router.navigate(`/ride-offer/${rideOffer.id}`)
  }

  navigateToNewOfferView() {
    if (this.rideOffersBtnClicked) {
      localStorage.removeItem("selected-ride-offer");
      localStorage.removeItem("selected-ride-request");
      this.router.navigate(['/ride-offer']);

    }
    if (this.rideRequestsBtnClicked) {
      localStorage.removeItem("selected-ride-request");
      localStorage.removeItem("selected-ride-offer");
      this.router.navigate(['/ride-request']);
    }
  }


}
