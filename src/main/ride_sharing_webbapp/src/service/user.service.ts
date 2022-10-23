import { Injectable } from '@angular/core';
import {HttpBackend, HttpClient} from "@angular/common/http";
import {Login} from "../model/Login";
import {AppSettings} from "../environments/AppSettings";
import {Router} from "@angular/router";
import {User} from "../model/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private router: Router) { }



  getUser(id: string | null) {
    return this.http.get(AppSettings.STR_URL_GET_USER_BY_ID + id, AppSettings.httpOptions);
  }

  registerUser(user: User) {
    return this.http.post<any>(AppSettings.STR_URL_POST_USER, AppSettings.httpOptions);
  }

  loginUser(login: Login) {
    return this.http.post<any>(AppSettings.STR_URL_LOGIN, login, AppSettings.httpOptions)
  }
}
