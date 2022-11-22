import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AppSettings } from "../environments/AppSettings";
import { Router } from "@angular/router";
import { User } from "../model/User";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private router: Router) { }

  getUser(id: number) {
    return this.http.get(AppSettings.STR_URL_GET_USER_BY_ID + id, AppSettings.httpOptions);
  }

  registerUser(user: User) {
    return this.http.post<User>(AppSettings.STR_URL_POST_USER, user, AppSettings.httpOptions);
  }

  updateUser(user: User) {
    return this.http.put<any>(AppSettings.STR_URL_PUT_USER, user, AppSettings.httpOptions)
  }

  deleteUser(id: number) {
    return this.http.delete(AppSettings.STR_URL_DELETE_USER + id, AppSettings.httpOptions)
  }
}
