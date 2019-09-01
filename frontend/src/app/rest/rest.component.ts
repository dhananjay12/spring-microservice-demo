import { RestService } from './../services/rest.service';
import { Component, OnInit } from '@angular/core';


@Component({
  selector: 'app-rest',
  templateUrl: './rest.component.html',
  styleUrls: ['./rest.component.css']
})
export class RestComponent implements OnInit {

  userResult: string;
  contactUsResult: string;
  userResultObj: any;
  userResultArr: any;
  headerResult: any

  constructor(private service: RestService) { }

  ngOnInit() {
    this.service.getAddress().subscribe(result=> this.userResult=result);
    this.service.getContactUsAddress().subscribe(result=> this.contactUsResult=result);
  }

  getUser(id){
    this.service.getUser(id).subscribe(result=> this.userResultObj=result);
  }

  sendHeader(headerKey,HeaderValue){
    this.service.sendHeader(headerKey,HeaderValue).subscribe(result=> this.headerResult=result);
  }

  getUsers(queryParameters){
    this.service.getUsers(queryParameters).subscribe(result=> this.userResultArr=result);
  }

}
