import { Injectable } from '@angular/core';
import {Login} from "../model/Login";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";
import {JwtHelperService} from "@auth0/angular-jwt";

const jwtHelper = new JwtHelperService();

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  loginUser(login: Login) {
    return this.http.post<any>(AppSettings.STR_URL_LOGIN, login, AppSettings.httpOptions);
  }

  isAuthenticated(): boolean {
    let token = localStorage.getItem("token");
    if (token == null) {
      console.error("No Token stored");
      return false;
    }
    return !jwtHelper.isTokenExpired(token);
  }

  getHttpTokenOptions() {
    let token = localStorage.getItem("token");
    if (token == null) {
      console.error("No Token stored");
      return;
    }
    let httpTokenOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + token
      })
    }
    return httpTokenOptions;
  }

}
