import { Component, OnInit } from '@angular/core';
import { Login } from "../../model/Login";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(data: any) {
    let login: Login = {
      email: data.email,
      password: data.password
    }
    console.log("logging in user: " + login.email);
    localStorage.setItem("userId", "1")
    this.router.navigate(["/profile"]);
    this.userService.loginUser(login).subscribe(token => {
      if (!token) {
        //this.router.navigate(['/login']);
        return
      }
      localStorage.setItem("token", token);
      console.log("logged in successfully");
    });
  }

  navigateToRegister() {
    console.log("navigating...");
    this.router.navigate(['/register']);
  }

}
