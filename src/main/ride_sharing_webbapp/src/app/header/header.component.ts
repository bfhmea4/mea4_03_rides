import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import { faBars } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  faBars = faBars
  navShown: boolean = false

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  logout(): void {
    localStorage.removeItem("userId")
    localStorage.removeItem("token")
    this.router.navigate(['/login'])
  }

  toggleNav(): void {
    this.navShown = !this.navShown
  }

}
