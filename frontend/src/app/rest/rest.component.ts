import { RestService } from './../services/rest.service';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-rest',
  templateUrl: './rest.component.html',
  styleUrls: ['./rest.component.css']
})
export class RestComponent implements OnInit {

  serviceOneResult: string;
  serviceTwoResult: string;
  userResultObj: any;
  headerResult: any
  hopResult: string;
  messageResult: string;
  divisionResult: string;

  constructor(private service: RestService) { }

  ngOnInit() {
    this.service.helloFromServiceOne().subscribe(result=> this.serviceOneResult=result);
    this.service.helloFromServiceTwo().subscribe(result=> this.serviceTwoResult=result);
  }

  hop(status){
    this.service.hop(status).subscribe(result=> this.hopResult=result);
  }

  sendHeader(headerKey,HeaderValue){
    this.service.sendHeader(headerKey,HeaderValue).subscribe(result=> this.headerResult=result);
  }

  messageServiceOne(text){
    this.service.messageServiceOne(text).subscribe(result=> this.messageResult=result);
  }

  divisionServiceTwo(num1,num2){
    this.service.divisionServiceTwo(num1,num2).subscribe(result=> this.divisionResult=result);
  }

}
