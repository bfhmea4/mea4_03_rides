import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable()
export class FizzbuzzService {

  constructor(private http: HttpClient) {}

  postNumber(number: string) {
    console.log("sending GET request...");

    const headers = new HttpHeaders()
      .append('Content-Type', 'application/json')
      .append('Access-Control-Allow-Headers', 'Content-Type')
      .append('Access-Control-Allow-Methods', 'GET')
      .append('Access-Control-Allow-Origin', '*');

    return this.http.get("http://localhost:8080/fizzbuzz/" + number, {headers});
  }


}
