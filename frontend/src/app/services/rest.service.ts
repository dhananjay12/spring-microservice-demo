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

  hop(status) {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/api/service-one/hop/"+status, {
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
      .get("/api/service-one/headers", {
        headers
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  helloFromServiceOne() {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/api/service-one/hello", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }

  helloFromServiceTwo() {
    const headers = new HttpHeaders().set(
      "Content-Type",
      "text/plain; charset=utf-8"
    );

    return this.httpClient
      .get("/api/service-two/hello", {
        headers,
        responseType: "text"
      })
      .pipe(catchError(this.errorHandlerService.handleError));
  }
}
