import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {SagaResidentDto, SagaResponseDto} from "../typings";

@Injectable({
  providedIn: 'root'
})
export class SynchronizationErrorLogService {

  constructor(private http: HttpClient) { }

  public getAllSynchronizationErrorLogs(): Observable<SagaResponseDto[]> {
    return this.http.get<SagaResponseDto[]>("http://localhost:8088/orchestrator/saga-responses");
  }

  public getAllFailedResidentSynchronization(): Observable<SagaResidentDto[]> {
    return this.http.get<SagaResidentDto[]>("http://localhost:8088/orchestrator/saga-residents");
  }
}
