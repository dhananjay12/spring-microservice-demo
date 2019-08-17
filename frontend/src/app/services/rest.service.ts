import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ErrorHandlerService } from "./error-handler.service";
import { catchError } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class RestService {
  constructor(
    private httpClient: HttpClient,
    private errorHandlerService: ErrorHandlerService
  ) {}

  getAddress() {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/user-service/users/getPublicMailingAddress", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }
  getContactUsAddress() {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/contact-us-service/contactUs/address", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }
}
