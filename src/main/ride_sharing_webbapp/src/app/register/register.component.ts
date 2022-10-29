import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(data: any) {
      this.router.navigate(["/profile"]);
    //TODO: Check if the 2 Passwords are the same
    let user: User = {
      id: 0,
      firstName: data.firstName,
      lastName: data.lastName,
      email: data.email,
      address: data.address,
      password: data.password
    }
    this.userService.registerUser(user).subscribe(token => {
      if (!token) {
        console.error("could not register, something went wrong");
      }
      localStorage.setItem("token", token);
      this.router.navigate(["/profile"]);
    })
  }

}
