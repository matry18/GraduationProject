import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {DemoDto} from "../../typings/typings/typings.module";

@Injectable({
  providedIn: 'root'
})
export class DemoService {

  constructor(private http: HttpClient) { }


  public getDemoText(requestString: string): Observable<DemoDto> {
    return this.http.get<DemoDto>(requestString);
  }

}
