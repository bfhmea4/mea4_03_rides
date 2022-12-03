import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { AppSettings } from "../environments/AppSettings";
import { Router } from "@angular/router";
import { User } from "../model/User";
import {AuthenticationService} from "./authentication.service";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private authService: AuthenticationService,
              private router: Router) { }

  getById(id: number) {
    return this.http.get<User>(AppSettings.STR_URL_GET_USER_BY_ID + id, AppSettings.httpOptions);
  }

  getByToken() {
    return this.http.get<User>(AppSettings.STR_URL_GET_USER_BY_TOKEN, this.authService.getHttpTokenOptions());
  }

  registerUser(user: User) {
    return this.http.post<any>(AppSettings.STR_URL_POST_USER, user, AppSettings.httpOptions);
  }

  updateUser(user: User) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_USER, user, this.authService.getHttpTokenOptions())
  }

  deleteUser(id: number) {
    return this.http.delete(AppSettings.STR_URL_DELETE_USER + id, this.authService.getHttpTokenOptions())
  }
}
