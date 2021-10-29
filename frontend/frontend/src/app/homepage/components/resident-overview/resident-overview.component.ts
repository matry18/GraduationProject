import { Component, OnInit } from '@angular/core';
import {ResidentService} from "../../../citizen/resident.service";

@Component({
  selector: 'resident-overview',
  templateUrl: './resident-overview.component.html',
  styleUrls: ['./resident-overview.component.css']
})
export class ResidentOverviewComponent implements OnInit {

  public residentCount: number = 0;
  constructor(private residentService: ResidentService) { }

  ngOnInit(): void {
    this.residentService.getResidentCount().subscribe((
      residents: number
    ) =>{
      this.residentCount = residents;
    })
  }

}
