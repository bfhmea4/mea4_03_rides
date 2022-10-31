import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FizzbuzzComponent } from './fizzbuzz/fizzbuzz.component';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {FizzbuzzService} from "../service/fizzbuzz.service";
import { LoginComponent } from './login/login.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { OverviewComponent } from './offers-requests-overview/overview.component';
import { HeaderComponent } from './header/header.component';
import { CreateUpdateRideOfferComponent } from './create-update-ride-offer/create-update-ride-offer.component';
import { CreateUpdateRideRequestComponent } from './create-update-ride-request/create-update-ride-request.component';

@NgModule({
  declarations: [
    AppComponent,
    FizzbuzzComponent,
    LoginComponent,
    ProfileComponent,
    RegisterComponent,
    OverviewComponent,
    HeaderComponent,
    CreateUpdateRideOfferComponent,
    CreateUpdateRideRequestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [FizzbuzzService],
  bootstrap: [AppComponent]
})
export class AppModule { }
