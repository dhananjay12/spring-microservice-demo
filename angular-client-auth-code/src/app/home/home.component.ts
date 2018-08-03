import { Component, OnInit } from "@angular/core";
import { AppService } from "../shared/auth/app.service";

@Component({
  selector: "app-home",
  providers: [AppService],
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.css"]
})
export class HomeComponent {
  public isLoggedIn = false;

  constructor(private _service: AppService) {}

  ngOnInit() {
    this.isLoggedIn = this._service.checkCredentials();
    let i = window.location.href.indexOf("code");
    if (!this.isLoggedIn && i != -1) {
      this._service.retrieveToken(window.location.href.substring(i + 5));
    }
  }

  login() {
    window.location.href =
      "https://dev-298555.oktapreview.com/oauth2/default/v1/authorize?response_type=code&client_id=" +
      this._service.clientId +
      "&redirect_uri=" +
      this._service.redirectUri +
      "&state=my-state&scope=openid";
  }

  logout() {
    this._service.logout();
  }
}
