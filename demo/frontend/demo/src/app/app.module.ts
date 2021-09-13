import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

import {DemoModule} from "./demo/demo.module";

@NgModule({
  declarations: [
    AppComponent

  ],
  imports: [
    DemoModule,
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
