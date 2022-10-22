import {HttpHeaders} from "@angular/common/http";

export class AppSettings {

  public static STR_URL_LOGIN: string = "http://localhost:8080/api/login";

  public static httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorisation: 'my-auth-token'
    })
  };

}
