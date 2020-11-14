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
      .get("/api/user-service/address/company", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  sendHeader(headerKey,HeaderValue){
    const headers = new HttpHeaders().set(
      headerKey,HeaderValue
    );

    return this.httpClient
      .get("/api/user-service/users/headers", {
        headers
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  getUser(id) {
    return this.httpClient
      .get("/api/user-service/users/"+id)
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  getUsers(queryParameters) {
    return this.httpClient
      .get("/api/user-service/users/multiple"+queryParameters)
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  getContactUsAddress() {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/api/contact-us-service/contactUs/address", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }
}
