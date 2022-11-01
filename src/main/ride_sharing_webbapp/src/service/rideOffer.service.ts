import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";
import {RideOffer} from "../model/RideOffer";

@Injectable({
  providedIn: 'root'
})
export class rideOfferService {

  constructor(private http: HttpClient,
              private router: Router) {
  }


  getAllOffers() {
    return this.http.get(AppSettings.STR_URL_GET_ALL_OFFERS, AppSettings.httpOptions);
  }

  updateOffer(rideOfferToUpdate: RideOffer) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_OFFER + rideOfferToUpdate.id, rideOfferToUpdate, AppSettings.httpOptions);

  }

  createOffer(rideOfferToCreate: RideOffer) {
    return this.http.post<any>(AppSettings.STR_URL_POST_OFFER, rideOfferToCreate, AppSettings.httpOptions);
  }

  deleteOffer(id: number) {
    return this.http.delete(AppSettings.STR_URL_DELETE_OFFER + id, AppSettings.httpOptions)
  }
}


