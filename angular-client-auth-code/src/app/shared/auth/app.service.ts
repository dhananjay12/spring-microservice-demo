import { Injectable } from "@angular/core";
import { Cookie } from "ng2-cookies";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { throwError as observableThrowError, Observable } from "rxjs";

import { catchError, map } from "rxjs/operators";

export class Foo {
  constructor(public id: number, public name: string) {}
}

@Injectable()
export class AppService {
  public clientId = "0oaftef7emaTmaT7F0h7";
  public redirectUri = "http://localhost:4200";

  constructor(private _http: HttpClient) {}

  retrieveToken(code) {
    let params = new URLSearchParams();
    params.append("grant_type", "authorization_code");
    params.append("client_id", this.clientId);
    params.append("redirect_uri", this.redirectUri);
    params.append("code", code);

    let headers = new HttpHeaders({
      "Content-type": "application/x-www-form-urlencoded; charset=utf-8"
    });
    this._http
      .post("http://localhost:8081/token", params.toString(), {
        headers: headers
      })
      .subscribe(
        data => this.saveToken(data),
        err => alert("Invalid Credentials")
      );
  }

  saveToken(token) {
    var expireDate = new Date().getTime() + 1000 * token.expires_in;
    Cookie.set("access_token", token.access_token, expireDate);
    console.log("Obtained Access token");
    window.location.href = "http://localhost:4200";
  }

  getResource(resourceUrl): Observable<any> {
    var headers = new HttpHeaders({
      "Content-type": "application/x-www-form-urlencoded; charset=utf-8",
      Authorization: "Bearer " + Cookie.get("access_token")
    });
    return this._http.get(resourceUrl, { headers: headers }).pipe(
      map(response => response),
      catchError(this.handleError)
    );
  }

  checkCredentials() {
    return Cookie.check("access_token");
  }

  logout() {
    Cookie.delete("access_token");
    window.location.reload();
  }

  private handleError(error: Response) {
    if (error.status === 400) {
      return observableThrowError(error);
    }
    if (error.status === 404) {
      return observableThrowError(error);
    }
    return observableThrowError(error);
  }
}
