import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { RestComponent } from './rest/rest.component';
import { WebsocketComponent } from './websocket/websocket.component';
import { ReactiveComponent } from './reactive/reactive.component';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http'; 
import { RestService } from './services/rest.service';
import { WebsocketService } from './services/websocket.service';
import { ReactiveService } from './services/reactive.service';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RestComponent,
    WebsocketComponent,
    ReactiveComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent },
      { path: 'rest', component: RestComponent },
      { path: 'reactive', component: ReactiveComponent },
      { path: 'websocket', component: WebsocketComponent }
    ])
  ],
  providers: [RestService,WebsocketService,ReactiveService],
  bootstrap: [AppComponent]
})
export class AppModule { }
