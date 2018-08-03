import { Injectable } from "@angular/core";
import { HttpClient} from '@angular/common/http'; 

@Injectable({
  providedIn: "root"
})
export class ProductService {
  private url = "http://localhost:8081/product-service";

  constructor(private http: HttpClient ) {}

  greet() {
    return this.http.get(this.url + "/hello");
  }
}
