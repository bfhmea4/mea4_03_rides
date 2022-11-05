import { Injectable } from '@angular/core';
import {Login} from "../model/Login";
import {HttpClient} from "@angular/common/http";
import {AppSettings} from "../environments/AppSettings";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  loginUser(login: Login) {
    console.log("loginobject in service: " + login.email);
    return this.http.post<any>(AppSettings.STR_URL_LOGIN, login, AppSettings.httpOptions);
  }

}
