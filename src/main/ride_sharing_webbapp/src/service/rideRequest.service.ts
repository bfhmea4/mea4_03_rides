import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";
import {RideRequest} from "../model/RideRequest";

@Injectable({
  providedIn: 'root'
})
export class rideRequestService {

  constructor(private http: HttpClient,
              private router: Router) {
  }


  getAllRequests() {
    return this.http.get(AppSettings.STR_URL_GET_ALL_REQUESTS, AppSettings.httpOptions);
  }

  updateRequest(rideRequestToUpdate: RideRequest) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_REQUEST + rideRequestToUpdate.id, rideRequestToUpdate,
      AppSettings.httpOptions)
  }

  createRequest(rideRequestToCreate: RideRequest) {
    return this.http.post<any>(AppSettings.STR_URL_POST_REQUEST, rideRequestToCreate, AppSettings.httpOptions)

  }

  deleteRequest(id: number) {
    return this.http.delete(AppSettings.STR_URL_DELETE_REQUEST + id, AppSettings.httpOptions)
  }
}
