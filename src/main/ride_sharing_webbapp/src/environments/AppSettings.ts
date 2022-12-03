import {HttpHeaders} from "@angular/common/http";

export class AppSettings {

  public static STR_URL_LOGIN:              string = "http://localhost:8080/api/login";
  public static STR_URL_GET_USER_BY_ID:     string = "http://localhost:8080/api/user/";
  public static STR_URL_GET_USER_BY_TOKEN:     string = "http://localhost:8080/api/user/byToken";
  public static STR_URL_POST_USER:          string = "http://localhost:8080/api/user/";
  public static STR_URL_PUT_USER:           string = "http://localhost:8080/api/user/";
  public static STR_URL_DELETE_USER:           string = "http://localhost:8080/api/user/";
  public static STR_URL_GET_ALL_OFFERS:     string = "http://localhost:8080/api/offers/";
  public static STR_URL_PUT_OFFER:          string = "http://localhost:8080/api/offer/";
  public static STR_URL_POST_OFFER:         string = "http://localhost:8080/api/offer/";
  public static STR_URL_DELETE_OFFER:       string = "http://localhost:8080/api/offer/";
  public static STR_URL_GET_ALL_REQUESTS:   string = "http://localhost:8080/api/requests/";
  public static STR_URL_PUT_REQUEST:        string = "http://localhost:8080/api/requests/";
  public static STR_URL_POST_REQUEST:       string = "http://localhost:8080/api/requests/";
  public static STR_URL_DELETE_REQUEST:     string = "http://localhost:8080/api/requests/";




  public static httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      Authorisation: 'my-auth-token'
    })
  };

}
