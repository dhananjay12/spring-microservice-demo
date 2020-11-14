import { Injectable } from "@angular/core";
import { HttpClient,HttpHeaders} from '@angular/common/http';
import { Cookie } from "ng2-cookies"; 
import { catchError, map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class ProductService {
  private url = "http://localhost:8081/product-service";

  constructor(private http: HttpClient ) {}

  greet() {
    var headers = new HttpHeaders({
      Authorization: "Bearer " + Cookie.get("jwt_token")
    });
    return this.http.get(this.url + "/products", { headers: headers });
  }
}
