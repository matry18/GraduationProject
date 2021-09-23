import { Injectable } from '@angular/core';
import {DepartmentDto} from "../typings";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) { }


  public createDepartment(departmentDto: DepartmentDto): Observable<DepartmentDto> {
    return this.http.post<DepartmentDto>("http://localhost:8080/bosted/department", departmentDto);
  }

  public getAllDepartments(): Observable<DepartmentDto[]> {
    return this.http.get<DepartmentDto[]>("http://localhost:8080/bosted/department");
  }
}
