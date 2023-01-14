import {Component, OnDestroy, OnInit} from '@angular/core';
import {rideOfferService} from "../../service/rideOffer.service";
import {Router} from "@angular/router";
import {RideOffer} from "../../model/RideOffer";
import {rideRequestService} from "../../service/rideRequest.service";
import {RideRequest} from "../../model/RideRequest";
import {User} from "../../model/User";
import {NgToastService} from 'ng-angular-popup';
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
  rideRequestsDisplayed: RideRequest[] = [];
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

  selectedDateString: string = '';
  selectedDate: Date = new Date();
  dateFilterActive: boolean = false
  selectedFromLocation: string = '';
  selectedToLocation: string = '';


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
    if (false) {
      // this.rideOffers = JSON.parse(offersListStorage);
      this.rideOffersDisplayed = this.rideOffers;
      this.getLocations(true);
      console.log("Get all offer via storage.....")
    } else {
      console.log("sending GET ALL OFFERS request...");
      this.rideOffers = [];
      this.rideOfferService.getAllOffers().subscribe(offers => {
        let temp = <any[]>offers;
        temp.forEach(offer => {
          let numbers = <number[]>offer.startTime;
          offer.startTime = new Date(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4]);
        })
        this.rideOffers = <RideOffer[]>temp;
        this.rideOffersDisplayed = this.rideOffers;
        this.getLocations(true);
        localStorage.setItem("ride-offers", JSON.stringify(this.rideOffers));
      })
    }
  }

  getAllRequests() {
    let requestsListStorage: string | null = localStorage.getItem("ride-requests")
    if (false) {
      // this.rideRequests = JSON.parse(requestsListStorage);
      this.rideRequestsDisplayed = this.rideRequests;
      this.getLocations(false);
      console.log("Get all requests via storage.....")
    } else {
      console.log("sending GET ALL REQUESTS request...");
      this.rideRequests = [];
      this.rideRequestService.getAllRequests().subscribe(requests => {
        let temp = <any[]>requests;
        temp.forEach(request => {
          let numbers = <number[]>request.startTime;
          request.startTime = new Date(numbers[0], numbers[1], numbers[2], numbers[3], numbers[4]);
        })
        this.rideRequests = <RideRequest[]>temp;
        this.rideRequestsDisplayed = this.rideRequests;
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

  filter() {
    this.rideOffersDisplayed = this.rideOffers;
    this.rideRequestsDisplayed = this.rideRequests;
    this.filterByDate();
    this.filterByFromLocation();
    this.filterByToLocation();
  }

  initiateFilterByDate(event: string) {
    this.selectedDate = new Date(event);
    this.dateFilterActive = true;
    this.filter();
  }

  filterByDate() {
    if (!this.selectedDate || !this.dateFilterActive) {
      return;
    }
    this.rideOffersDisplayed = this.rideOffersDisplayed.filter(offer => {
      let offerDate = new Date(offer.startTime);
      if (offerDate.getFullYear() == this.selectedDate.getFullYear() &&
        offerDate.getMonth() == this.selectedDate.getMonth() &&
        offerDate.getDay() == this.selectedDate.getDay()) {
        return offer;
      }
      this.dateFilterActive = false;
      return;
    });

    this.rideRequestsDisplayed = this.rideRequestsDisplayed.filter(request => {
      let offerDate = new Date(request.startTime);
      if (offerDate.getFullYear() == this.selectedDate.getFullYear() &&
        offerDate.getMonth() == this.selectedDate.getMonth() &&
        offerDate.getDay() == this.selectedDate.getDay()) {
        return request;
      }
      this.dateFilterActive = false;
      return;
    });

  }

  filterByFromLocation() {
    if (!this.selectedFromLocation!) {
      return;
    }
    this.rideOffersDisplayed = this.rideOffersDisplayed.filter(offer => {
      return offer.fromAddress.location == this.selectedFromLocation
    });
    this.rideRequestsDisplayed = this.rideRequestsDisplayed.filter(offer => {
      return offer.fromAddress.location == this.selectedFromLocation
    });
  }

  filterByToLocation() {
    if (!this.selectedToLocation) {
      return;
    }
    this.rideOffersDisplayed = this.rideOffersDisplayed.filter(offer => {
      return offer.toAddress.location == this.selectedToLocation
    });
    this.rideRequestsDisplayed = this.rideRequestsDisplayed.filter(offer => {
      return offer.toAddress.location == this.selectedToLocation
    });
  }

  filterFromLocation(event: Event) {
    // @ts-ignore
    this.selectedFromLocation = event.target.value;
    this.filter();
  }

  filterToLocation(event: Event) {
    // @ts-ignore
    this.selectedToLocation = event.target.value;
    this.filter();
  }

  resetFilters() {
    this.dateFilterActive = false;
    this.selectedDateString = '';
    this.selectedFromLocation = '';
    this.selectedToLocation = '';
    this.filter();
  }
}
