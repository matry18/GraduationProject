import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EmployeeDto} from "../typings";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) { }

  public createEmployee(employeeDto: EmployeeDto): Observable<EmployeeDto> {
    return this.http.post<EmployeeDto>("http://localhost:8080/bosted/employees", employeeDto);
  }

  public getAllEmployees(): Observable<EmployeeDto[]> {
    return this.http.get<EmployeeDto[]>("http://localhost:8080/bosted/employees");
  }

  public deleteEmployee(id: string): Observable<EmployeeDto> {
    return this.http.delete<EmployeeDto>("http://localhost:8080/bosted/employees/"+id);
  }

  public editEmployee(employee: EmployeeDto): Observable<EmployeeDto> {
    return this.http.put<EmployeeDto>("http://localhost:8080/bosted/employees", employee);
  }

  public getEmployeeCount(): Observable<number> {
    return this.http.get<number>("http://localhost:8080/bosted/employeeCount");
  }

}
