import { Component, OnInit } from '@angular/core';
import { UserService } from "../../service/user.service";
import { Router } from "@angular/router";
import { User } from "../../model/User";

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
      // this.router.navigate(["/profile"]);
    //TODO: Check if the 2 Passwords are the same
    console.log(data.firstName);
      console.log("wtf");
      let user: User = {
        id: 1,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        address: data.address,
        password: data.password
    }
    this.userService.registerUser(user).subscribe(user => {
      if (user) {
        localStorage.setItem("id", String(user.id));
        // console.log("returned user with id: " + user.id);
        // localStorage.setItem("token", token);
        // this.router.navigate(["/profile"]);
      }
        console.error("could not register, something went wrong");
    })
  }

}
