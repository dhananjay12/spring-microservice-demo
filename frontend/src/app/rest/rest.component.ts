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

  constructor(private service: RestService) { }

  ngOnInit() {
    this.service.getAddress().subscribe(result=> this.userResult=result);
    this.service.getContactUsAddress().subscribe(result=> this.contactUsResult=result);
  }

}
