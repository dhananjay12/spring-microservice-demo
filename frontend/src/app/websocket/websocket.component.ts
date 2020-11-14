import { Component, OnInit } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-websocket',
  templateUrl: './websocket.component.html',
  styleUrls: ['./websocket.component.css']
})
export class WebsocketComponent implements OnInit {

  greetings: Array<any> = [];
  name: string;
  webSocketEndPoint: string = '/api/websocketservice/gs-guide-websocket';
  topic: string = "/topic/greetings";
  stompClient: any;
  buttonDisabled: boolean = false;
  ngOnInit() {    
  }

  connect(){
    this.buttonDisabled=true;
   let ws = new SockJS(this.webSocketEndPoint);
        this.stompClient = Stomp.over(ws);
        const _this = this;        
        _this.stompClient.connect({}, function (frame) {
            _this.stompClient.subscribe(_this.topic, function (sdkEvent) {
                _this.handleMessage(sdkEvent);
            });
            //_this.stompClient.reconnect_delay = 2000;
        }, this.errorCallBack);
  }

  disconnect(){
   // this.webSocketAPI._disconnect();
   if (this.stompClient !== null) {
    this.stompClient.disconnect();
    this.buttonDisabled=false;
    }
  }

  sendMessage(){
    //this.webSocketAPI._send(this.name);
    console.log("calling logout api via web socket");
    this.stompClient.send("/app/hello", {}, JSON.stringify(this.name));
  }

  handleMessage(message){
    this.greetings.push(JSON.parse(message.body).content);
  }

  // on error, schedule a reconnection attempt
  errorCallBack(error) {
      console.log("errorCallBack -> " + error)
      setTimeout(() => {
          this.connect();
      }, 5000);
  }

}
