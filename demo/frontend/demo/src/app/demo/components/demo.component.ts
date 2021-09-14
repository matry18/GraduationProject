import { Component, OnInit } from '@angular/core';
import {DemoService} from "./demo.service";
import {DemoDto} from "../../typings/typings/typings.module";

@Component({
  selector: 'app-demo',
  templateUrl: './demo.component.html',
  styleUrls: ['./demo.component.css']
})
export class DemoComponent implements OnInit {

  public demoText: string = '';
  public endpoint: string = 'http://backend-service/helloWorld';
  constructor(private demoService: DemoService) { }

 public ngOnInit(): void {

   this.demoService.getDemoText(this.endpoint).subscribe((demo: any)=>
     this.demoText = demo.text
   );
  }

}
