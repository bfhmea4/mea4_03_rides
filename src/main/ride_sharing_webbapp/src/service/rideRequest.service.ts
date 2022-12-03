import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";
import {RideRequest} from "../model/RideRequest";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class rideRequestService {

  constructor(private http: HttpClient,
              private authService: AuthenticationService,
              private router: Router) {
  }


  getAllRequests() {
    return this.http.get(AppSettings.STR_URL_GET_ALL_REQUESTS, this.authService.getHttpTokenOptions());
  }

  updateRequest(rideRequestToUpdate: RideRequest) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_REQUEST + rideRequestToUpdate.id, rideRequestToUpdate,
      this.authService.getHttpTokenOptions())
  }

  createRequest(rideRequestToCreate: RideRequest) {
    return this.http.post<any>(AppSettings.STR_URL_POST_REQUEST, rideRequestToCreate, this.authService.getHttpTokenOptions())

  }

  deleteRequest(rideRequestToDelete: RideRequest) {
    return this.http.delete(AppSettings.STR_URL_DELETE_REQUEST + rideRequestToDelete.id, this.authService.getHttpTokenOptions())
  }
}
