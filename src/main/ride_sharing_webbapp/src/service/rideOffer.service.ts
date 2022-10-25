import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class rideOfferService {

  constructor(private http: HttpClient,
              private router: Router) { }


  getAllOffers() {
    return this.http.get(AppSettings.STR_URL_GET_ALL_OFFERS, AppSettings.httpOptions);
  }

}


