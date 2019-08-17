import { ReactiveService } from './../services/reactive.service';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-reactive',
  templateUrl: './reactive.component.html',
  styleUrls: ['./reactive.component.css']
})
export class ReactiveComponent implements OnInit {

  values: Array<any> = [];

  constructor(private service: ReactiveService) { }

  ngOnInit() {
    this.service.getSSE().subscribe(response => {this.values.push(response.data)})    
  }


}
