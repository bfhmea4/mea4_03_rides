import {Component, OnDestroy, OnInit} from '@angular/core';
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {RideOffer} from "../../model/RideOffer";
import {rideRequestService} from "../../service/rideRequest.service";
import {RideRequest} from "../../model/RideRequest";
import {User} from "../../model/User";
import { NgToastService } from 'ng-angular-popup';
import {AuthenticationService} from "../../service/authentication.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-ride-offer',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit, OnDestroy {

  rideOffers: RideOffer[] = [];
  rideRequests: RideRequest[] = [];
  rideOffersDisplayed: RideOffer[] = [];
  rideRequestDisplayed: RideRequest[] = [];
  rideRequestsBtnClicked: boolean = false;
  rideOffersBtnClicked: boolean = true;

  offersToLocations: string[] = [];
  offersFromLocations: string[] = [];
  requestsToLocations: string[] = [];
  requestsFromLocations: string[] = [];

  selectedFromLocationOffer: string = '';
  selectedToLocationOffer: string = '';
  selectedFromLocationRequest: string = '';
  selectedToLocationRequest: string = '';

  isLoggedIn: boolean = false;
  userString: string | null = localStorage.getItem("user");
  user?: User;


  constructor(private rideOfferService: rideOfferService,
              private rideRequestService: rideRequestService,
              private router: Router,
              private userService: UserService,
              private authService: AuthenticationService,
              private toast: NgToastService) {

  }

  ngOnInit(): void {
    // Fetch user
    if (!this.authService.isAuthenticated()) {
      console.error("not Authenticated!");
      this.router.navigate(['/login']);
    }
    let token = localStorage.getItem("token");
    if (token != null) {
      this.userService.getByToken().subscribe(user => {
        if (user == null) {
          console.error("could not get user");
          this.isLoggedIn = false;
          this.router.navigate(['/login']);
        }
        this.user = user;
        localStorage.setItem("user", JSON.stringify(this.user));
        this.isLoggedIn = true;
      });
    } else {
      console.error("you are not logged in!");
      this.isLoggedIn = false;
      this.router.navigate(['/login']);
    }

    if (localStorage.getItem("selected-ride-request")) {
      this.requestsBtnClicked()
    } else {
      this.offersBtnClicked()
    }
    localStorage.removeItem("selected-ride-request")
    localStorage.removeItem("selected-ride-offer")
  }

  ngOnDestroy(): void {
    localStorage.removeItem("ride-offers");
    localStorage.removeItem("ride-requests");
  }

  getLocations(searchInOffers: boolean): void {
    if (searchInOffers) {
      for (const offer of this.rideOffers) {
        if (!this.offersToLocations.includes(offer.toAddress.location)) {
          this.offersToLocations.push(offer.toAddress.location)
        }
      }
      for (const offer of this.rideOffers) {
        if (!this.offersFromLocations.includes(offer.fromAddress.location)) {
          this.offersFromLocations.push(offer.fromAddress.location)
        }
      }
      console.log('Offers to location: ' + this.offersToLocations.toString());
      console.log('Offers From location: ' + this.offersFromLocations.toString());
    }
    if (!searchInOffers) {
      for (const request of this.rideRequests) {
        if (!this.requestsFromLocations.includes(request.fromAddress.location)) {
          this.requestsFromLocations.push(request.fromAddress.location)
        }
      }
      for (const request of this.rideRequests) {
        if (!this.requestsToLocations.includes(request.toAddress.location)) {
          this.requestsToLocations.push(request.toAddress.location)
        }
      }
      console.log('Requests to location: ' + this.requestsToLocations.toString());
      console.log('Requests From location: ' + this.requestsToLocations.toString());
    }
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
      this.rideOffersDisplayed = this.rideOffers;
      this.getLocations(true);
      console.log("Get all offer via storage.....")
    } else {
      console.log("sending GET ALL OFFERS request...");
      this.rideOffers = [];
      this.rideOfferService.getAllOffers().subscribe(offers => {
        this.rideOffers = <RideOffer[]>offers;
        this.rideOffersDisplayed = this.rideOffers;
        this.getLocations(true);
        localStorage.setItem("ride-offers", JSON.stringify(this.rideOffers));
        console.log(localStorage.getItem("ride-offers"));
      })
    }
  }

  getAllRequests() {
    let requestsListStorage: string | null = localStorage.getItem("ride-requests")
    if (requestsListStorage) {
      this.rideRequests = JSON.parse(requestsListStorage);
      this.rideRequestDisplayed = this.rideRequests;
      this.getLocations(false);


      console.log("Get all requests via storage.....")
    } else {
      console.log("sending GET ALL REQUESTS request...");
      this.rideRequests = [];
      this.rideRequestService.getAllRequests().subscribe(requests => {
        this.rideRequests = <RideRequest[]>requests;
        this.rideRequestDisplayed = this.rideRequests;
        this.getLocations(false);
        localStorage.setItem("ride-requests", JSON.stringify(this.rideRequests));
      })
    }
  }

  onClickOfferItem(rideOffer: RideOffer) {
    //TODO method without TS ignore
    localStorage.setItem("selected-ride-offer", JSON.stringify(rideOffer));
    if(this.isLoggedIn) {
      // @ts-ignore
      this.router.navigate(['/ride-offer'])
    }else{
      this.toast.error(({detail:'Please Sign in', summary: 'Pleas log in to view details of an offer!', duration:5000}))
      this.router.navigate(['/login']);
    }
  }

  onClickRequestItem(rideRequest: RideRequest) {
    //TODO method without TS ignore
    localStorage.setItem("selected-ride-request", JSON.stringify(rideRequest));
    if(this.isLoggedIn) {
      // @ts-ignore
    this.router.navigate(['/ride-request']);
    }else{
      this.toast.error(({detail:'Please Sign in', summary: 'Pleas log in to view details of a request!',duration:5000}));
      this.router.navigate(['/login']);

    }
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


  filterOfferView(filterFrom: boolean, event: Event) {
    if (filterFrom) {
      // @ts-ignore
      this.selectedFromLocationOffer = event.target.value;
    }
    if (!filterFrom) {
      // @ts-ignore
      this.selectedToLocationOffer = event.target.value;
    }
    if (!this.selectedFromLocationOffer && !this.selectedToLocationOffer) {
      this.rideOffersDisplayed = this.rideOffers;
      return;
    }
    if (!this.selectedFromLocationOffer) {
      this.rideOffersDisplayed = this.rideOffers.filter(offer => {
        return offer.toAddress.location == this.selectedToLocationOffer
      })
      return;
    }
    if (!this.selectedToLocationOffer) {
      this.rideOffersDisplayed = this.rideOffers.filter(offer => {
        return offer.fromAddress.location === this.selectedFromLocationOffer
      })
      return;
    }
    this.rideOffersDisplayed = this.rideOffers.filter(offer => {
      return offer.fromAddress.location == this.selectedFromLocationOffer && offer.toAddress.location == this.selectedToLocationOffer
    })
  }

  filterRequestView(filterFrom: boolean, event: Event) {
    if (filterFrom) {
      // @ts-ignore
      this.selectedFromLocationRequest = event.target.value;
    }
    if (!filterFrom) {
      // @ts-ignore
      this.selectedToLocationRequest = event.target.value;
    }
    if (!this.selectedFromLocationRequest && !this.selectedToLocationRequest) {
      this.rideRequestDisplayed = this.rideRequests;
      return;
    }
    if (!this.selectedFromLocationRequest) {
      this.rideRequestDisplayed = this.rideRequests.filter(offer => {
        return offer.toAddress.location == this.selectedToLocationRequest
      })
      return;
    }
    if (!this.selectedToLocationRequest) {
      this.rideRequestDisplayed = this.rideRequests.filter(offer => {
        return offer.fromAddress.location === this.selectedFromLocationRequest
      })
      return;
    }
    this.rideRequestDisplayed = this.rideRequests.filter(offer => {
      return offer.fromAddress.location == this.selectedFromLocationRequest && offer.toAddress.location == this.selectedToLocationRequest
    })
  }
}
