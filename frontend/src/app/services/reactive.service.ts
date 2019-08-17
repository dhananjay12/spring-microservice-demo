import { SseService } from "./sse.service";
import { Injectable, NgZone } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { ErrorHandlerService } from "./error-handler.service";
import { Observable } from "rxjs";

@Injectable({
  providedIn: "root"
})
export class ReactiveService {
  constructor(
    private httpClient: HttpClient,
    private errorHandlerService: ErrorHandlerService,
    private sseService: SseService,
    private zone: NgZone
  ) {}

  getSSE() {
    return Observable.create(observer => {
      const eventSource = this.sseService.getEventSource(
        "/api/reactive-web-producer/greetings/sse"
      );

      eventSource.onmessage = event => {
        this.zone.run(() => {
          observer.next(event);
        });
      };

      eventSource.onerror = error => {
        this.zone.run(() => {
          observer.error(error);
        });
      };
    });
  }
}
