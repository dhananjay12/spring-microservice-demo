import { Injectable } from "@angular/core";
import { Http } from "@angular/http";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  private url = "http://localhost:8081/product-service";

  constructor(private http: Http) {}

  greet() {
    return this.http.get(this.url + "/hello");
  }
}
