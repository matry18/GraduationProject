import { Component, OnInit } from '@angular/core';
import {SynchronizationErrorLogService} from "../../synchronization-error-log.service";

@Component({
  selector: 'synchronization-errors',
  templateUrl: './synchronization-errors.component.html',
  styleUrls: ['./synchronization-errors.component.css']
})
export class SynchronizationErrorsComponent implements OnInit {

  public synchronizationErrorsCount: number = 0;
  constructor(private synchronizationErrorLogService: SynchronizationErrorLogService) { }

  ngOnInit(): void {
    this.synchronizationErrorLogService.getSynchronizationErrorCount().subscribe((
      synchronizedErrors: number
    ) => {
      this.synchronizationErrorsCount = synchronizedErrors;
    })
  }

}
