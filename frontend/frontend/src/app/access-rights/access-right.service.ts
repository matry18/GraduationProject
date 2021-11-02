import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccessRightDto} from "../typings";

@Injectable({
  providedIn: 'root'
})
export class AccessRightService {

  constructor(private http: HttpClient) { }


  public getAllAccessRights(): Observable<AccessRightDto[]> {
    return this.http.get<AccessRightDto[]>("http://localhost:8080/bosted/access-rights");
  }
}
