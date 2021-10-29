import { Component, OnInit } from '@angular/core';
import {ResidentService} from "../../../citizen/resident.service";

@Component({
  selector: 'resident-backedup',
  templateUrl: './resident-backedup.component.html',
  styleUrls: ['./resident-backedup.component.css']
})
export class ResidentBackedupComponent implements OnInit {

  public backedupResidentCount : number = 0;
  constructor(private residentService: ResidentService) { }

  ngOnInit(): void {
    this.residentService.getBackedupResidents().subscribe((
      backedupResidents: number
    ) => {
      this.backedupResidentCount = backedupResidents;
    })
  }

}
