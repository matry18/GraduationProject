import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {DemoComponent} from "./components/demo.component";
import {DemoService} from "./components/demo.service";
import {HttpClientModule} from "@angular/common/http";




@NgModule({
  declarations: [DemoComponent],
  exports: [
    DemoComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule
  ]

})
export class DemoModule { }
