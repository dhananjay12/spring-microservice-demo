import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { Routes, RouterModule } from '@angular/router';

import { HttpClientModule } from '@angular/common/http'; 

import { AppComponent } from "./app.component";
import { HomeComponent } from './home/home.component';
import { ProductsComponent } from "./products/products.component";
import { WebsocketsComponent } from './websockets/websockets.component';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ProductsComponent,
    WebsocketsComponent    
  ],
  imports: [
    BrowserModule,    
    HttpClientModule,
    RouterModule.forRoot([
     { path: '', component: HomeComponent, pathMatch: 'full' }], {onSameUrlNavigation: 'reload'})
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }