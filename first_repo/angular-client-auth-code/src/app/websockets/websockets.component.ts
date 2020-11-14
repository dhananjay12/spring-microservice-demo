import { Component, OnInit } from '@angular/core';
import { Cookie } from "ng2-cookies";
import { HttpClient,HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'websockets',
  templateUrl: './websockets.component.html',
  styleUrls: ['./websockets.component.css']
})
export class WebsocketsComponent{

  title = 'Websockets!';

  files: Array<FileEvent> = [];

  token = Cookie.get("jwt_token");

  private ws = new WebSocket('ws://localhost:8081/ws/files?access_token=Bearer '+this.token);

  //private ws = new WebSocket('ws://Authorization:Bearer '+this.token+'@localhost:8081/ws/files');

  //private ws = new WebSocket('ws://localhost:8081/ws/files');
  
  constructor() {
    this.ws.onmessage = (me: MessageEvent) => {
      const data = JSON.parse(me.data) as FileEvent;
      this.files.push(data);
    };
  }

}

export interface FileEvent {
  path: string;
  sessionId: string;
}