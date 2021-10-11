import { Injectable } from '@angular/core';
import {DepartmentDto} from "../typings";
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) { }
  private bostedApi:string = environment.bostedApi;

  public createDepartment(departmentDto: DepartmentDto): Observable<DepartmentDto> {
    return this.http.post<DepartmentDto>(this.bostedApi + "bosted/department", departmentDto);
  }

  public getAllDepartments(): Observable<DepartmentDto[]> {
    return this.http.get<DepartmentDto[]>(this.bostedApi + "bosted/department");
  }
}
