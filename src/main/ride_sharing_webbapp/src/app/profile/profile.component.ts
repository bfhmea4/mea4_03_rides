import { Component, OnInit } from '@angular/core';
import {UserService} from "../../service/user.service";
import {User} from "../../model/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user: Object = {} as User;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    this.userService.getUser(localStorage.getItem("userId")).subscribe(user => {
      if (!user) {
        this.router.navigate(['/login']);
      }
      this.user = user;
    })
  }

}
