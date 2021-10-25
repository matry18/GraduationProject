import { Component, OnInit } from '@angular/core';
import {SagaEmployeeDto} from "../../typings";
import {SynchronizationErrorLogService} from "../synchronization-error-log.service";

@Component({
  selector: 'failed-synchronization-employee-list',
  templateUrl: './failed-synchronization-employee-list.component.html',
  styleUrls: ['./failed-synchronization-employee-list.component.css']
})
export class FailedSynchronizationEmployeeListComponent implements OnInit {

  public sagaEmployees : SagaEmployeeDto[] = [];

  public displayedColumns = ['sagaId', 'id', 'firstname', 'lastname', 'department', 'email', 'phoneNumber'];

  constructor(private syncErrorLogService: SynchronizationErrorLogService) { }

  public ngOnInit(): void {
    this.getAllFailedSynchronizationEmployees();
  }

  private getAllFailedSynchronizationEmployees() {
    this.syncErrorLogService.getAllFailedEmployeeSynchronization().subscribe((sagaEmployees: SagaEmployeeDto[]) => {
      this.sagaEmployees = sagaEmployees;
    })
  }
}
