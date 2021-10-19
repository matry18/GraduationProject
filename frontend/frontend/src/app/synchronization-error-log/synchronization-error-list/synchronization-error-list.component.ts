import {Component, OnInit} from '@angular/core';
import {SagaResponseDto, SagaStatus} from "../../typings";
import {SynchronizationErrorLogService} from "../synchronization-error-log.service";


@Component({
  selector: 'synchronization-error-list',
  templateUrl: './synchronization-error-list.component.html',
  styleUrls: ['./synchronization-error-list.component.css']
})
export class SynchronizationErrorListComponent implements OnInit {

  public synchronizationErrors: SagaResponseDto[] = [];

  public displayedColumns = ['sagaId', 'serviceName', 'sagaStatus', 'receivingTime', 'errorMessage'];

  constructor(private syncErrorLogService: SynchronizationErrorLogService) { }

  public ngOnInit(): void {
    this.getAllSynchronizationErrors();

  }

  private getAllSynchronizationErrors(): void {
    this.syncErrorLogService.getAllSynchronizationErrorLogs()
      .subscribe((sagaResponses: SagaResponseDto[]) => {
        this.synchronizationErrors
          = sagaResponses.filter(sagaResponses => sagaResponses.sagaStatus == SagaStatus.FAILED)
          .sort((a, b) =>  new Date(b.receivingTime).getTime() -new Date(a.receivingTime).getTime())

      })
  }

  public getFirstLineOfMessage(message: string): string {
    return message.substr(0, 120)+'....';
  }
}
