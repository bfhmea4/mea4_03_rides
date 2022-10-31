import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";
import {User} from "../../model/User";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  @ViewChild('profileFirstname', { static: true }) profileFirstname!: ElementRef;
  @ViewChild('profileLastname', { static: true }) profileLastname!: ElementRef;
  @ViewChild('profileUsername', { static: true }) profileUsername!: ElementRef;
  @ViewChild('profileEmail', { static: true }) profileEmail!: ElementRef;
  @ViewChild('profileAddress', { static: true }) profileAddress!: ElementRef;

  user = {} as User;

  editing = false;

  constructor(private userService: UserService,
              private router: Router) { }

  ngOnInit(): void {
    let userId = localStorage.getItem("userId")
    if (!userId) {
      this.router.navigate(['/login']);
      return
    }

    this.userService.getUser(userId).subscribe(user => {
      if (!user) {
        this.router.navigate(['/login']);
        return
      }
      this.user = user as User;
    })
  }

  edit(): void {
    this.profileFirstname.nativeElement.contentEditable = true;
    this.profileLastname.nativeElement.contentEditable = true;
    this.profileUsername.nativeElement.contentEditable = true;
    this.profileEmail.nativeElement.contentEditable = true;
    this.profileAddress.nativeElement.contentEditable = true;

    this.editing = true;
  }

  cancel(): void {
    this.profileFirstname.nativeElement.contentEditable = false;
    this.profileLastname.nativeElement.contentEditable = false;
    this.profileUsername.nativeElement.contentEditable = false;
    this.profileEmail.nativeElement.contentEditable = false;
    this.profileAddress.nativeElement.contentEditable = false;

    this.editing = false;
  }

  save() {
    let user = {
      id: this.user.id,
      password: this.user.password,
      firstName: this.profileFirstname.nativeElement.innerHTML,
      lastName: this.profileLastname.nativeElement.innerHTML,
      userName: this.profileUsername.nativeElement.innerHTML,
      email: this.profileEmail.nativeElement.innerHTML,
      address: this.profileAddress.nativeElement.innerHTML
    } as User

    console.log("Updating profile of user: " + this.user.email);
    this.userService.updateUser(user).subscribe(user => {
      if (user) {
        this.user = user;
      }
      console.log("Updated successfully");
    });
  }

}
