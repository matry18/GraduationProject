import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {RoleDto} from "../typings";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http: HttpClient) { }

  public createRole(roleDto: RoleDto): Observable<RoleDto> {
    return this.http.post<RoleDto>("http://localhost:8080/bosted/roles", roleDto);
  }

  public getAllRoles(): Observable<RoleDto[]> {
    return this.http.get<RoleDto[]>("http://localhost:8080/bosted/roles");
  }

  public deleteRole(id: string): Observable<void> {
    return this.http.delete<void>(`http://localhost:8080/bosted/roles/${id}`);
  }
}
