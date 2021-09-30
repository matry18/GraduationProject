import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ResidentDto} from "../typings";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ResidentService {

  constructor(private http: HttpClient) { }

  public createCitizen(citizenDto: ResidentDto): Observable<ResidentDto> {
   return this.http.post<ResidentDto>("http://localhost:8080/bosted/citizen", citizenDto);
  }

  public getAllCitizen(): Observable<ResidentDto[]> { //måske skal vi tage department som argument?
    return this.http.get<ResidentDto[]>("http://localhost:8080/bosted/citizen");
  }

  public deleteCitizen(id: string): Observable<ResidentDto> {
    return this.http.delete<ResidentDto>("http://localhost:8080/bosted/citizen/"+id);
  }

  public editCitizen(resident: ResidentDto): Observable<ResidentDto> {
    return this.http.put<ResidentDto>("http://localhost:8080/bosted/citizen", resident);
  }
}
