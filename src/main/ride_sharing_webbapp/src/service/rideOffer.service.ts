import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";
import {RideOffer} from "../model/RideOffer";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class rideOfferService {

  constructor(private http: HttpClient,
              private authService: AuthenticationService,
              private router: Router) {
  }


  getAllOffers() {
    return this.http.get(AppSettings.STR_URL_GET_ALL_OFFERS, this.authService.getHttpTokenOptions());
  }

  updateOffer(rideOfferToUpdate: RideOffer) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_OFFER + rideOfferToUpdate.id, rideOfferToUpdate, this.authService.getHttpTokenOptions());

  }

  createOffer(rideOfferToCreate: RideOffer) {
    return this.http.post<any>(AppSettings.STR_URL_POST_OFFER, rideOfferToCreate, this.authService.getHttpTokenOptions());
  }

  deleteOffer(rideOfferToDelete: RideOffer) {
    return this.http.delete(AppSettings.STR_URL_DELETE_OFFER + rideOfferToDelete.id, this.authService.getHttpTokenOptions())
  }
}


