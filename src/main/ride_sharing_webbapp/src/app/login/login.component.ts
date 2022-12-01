import { Component, OnInit } from '@angular/core';
import { Login } from "../../model/Login";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../service/authentication.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthenticationService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(data: any) {
    let login: Login = {
      email: data.email,
      password: data.password
    }
    console.log("logging in user: " + login.email);
    this.authService.loginUser(login).subscribe(data => {
      if (data) {
        console.log("logged in User, got token: " + data.token);
        localStorage.setItem("token", data.token);
        this.router.navigate(['/profile']);
      } else {
        console.error("could not login user!!")
      }
    })
  }

  navigateToRegister() {
    console.log("navigating...");
    this.router.navigate(['/register']);
  }

}
