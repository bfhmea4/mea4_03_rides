import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {ProfileComponent} from "./profile/profile.component";
import {RegisterComponent} from "./register/register.component";
import {OverviewComponent} from "./offers-requests-overview/overview.component";
import {CreateUpdateRideOfferComponent} from "./create-update-ride-offer/create-update-ride-offer.component";
import {CreateUpdateRideRequestComponent} from "./create-update-ride-request/create-update-ride-request.component";

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'overview', component: OverviewComponent},
  {path: 'ride-offer', component: CreateUpdateRideOfferComponent},
  {path: 'ride-request', component: CreateUpdateRideRequestComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
