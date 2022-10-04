import { Component, OnInit } from '@angular/core';
import {FizzbuzzService} from "../../service/fizzbuzz.service";

@Component({
  selector: 'app-fizzbuzz',
  templateUrl: './fizzbuzz.component.html',
  styleUrls: ['./fizzbuzz.component.css']
})
export class FizzbuzzComponent implements OnInit {

  constructor(private fizzBuzzService: FizzbuzzService) { }

  ngOnInit(): void {
  }

  number = 0;
  result = "";

  sendNumber() {
    console.log(this.number);
    this.fizzBuzzService.postNumber(String(this.number)).subscribe(result => {
      console.log("received: " + result);
      this.result = result.toString();
    });
  }

}
