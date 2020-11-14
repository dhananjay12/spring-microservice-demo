import { Component, OnInit } from "@angular/core";
import { ProductService } from "./product.service";


@Component({
  selector: "app-products",
  templateUrl: "./products.component.html",
  styleUrls: ["./products.component.css"]
})
export class ProductsComponent implements OnInit {
  greet: any;
  constructor(private service: ProductService) {}

  ngOnInit() {  
    this.service.greet().subscribe(response => {
      this.greet = response;
    });
  }
}
