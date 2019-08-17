import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SseService {

  getEventSource(url:string):EventSource{
    return new EventSource(url);
  }
}
