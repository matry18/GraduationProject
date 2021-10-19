import { Component, OnInit } from '@angular/core';
import {SagaResidentDto} from "../../typings";
import {SynchronizationErrorLogService} from "../synchronization-error-log.service";

@Component({
  selector: 'failed-synchronization-resident-list',
  templateUrl: './failed-synchronization-resident-list.component.html',
  styleUrls: ['./failed-synchronization-resident-list.component.css']
})
export class FailedSynchronizationResidentListComponent implements OnInit {

  public sagaResidents : SagaResidentDto[] = [];

  public displayedColumns = ['sagaId', 'id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

  constructor(private syncErrorLogService: SynchronizationErrorLogService) { }

  public ngOnInit(): void {
    this.getAllFailedSynchronizationResidents();
  }


  private getAllFailedSynchronizationResidents() {
    this.syncErrorLogService.getAllFailedResidentSynchronization().subscribe((sagaResidents: SagaResidentDto[]) => {
      this.sagaResidents = sagaResidents;
    })
  }

}
