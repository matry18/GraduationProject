import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ResidentDto} from "../typings";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ResidentService {

  constructor(private http: HttpClient) { }
  private bostedApi:string = environment.bostedApi;

  public createCitizen(citizenDto: ResidentDto): Observable<ResidentDto> {
   return this.http.post<ResidentDto>(this.bostedApi + "bosted/citizen", citizenDto);
  }

  public getAllCitizen(): Observable<ResidentDto[]> { //måske skal vi tage department som argument?
    return this.http.get<ResidentDto[]>(this.bostedApi+ "bosted/citizen");
  }

  public deleteCitizen(id: string): Observable<ResidentDto> {
    return this.http.delete<ResidentDto>(this.bostedApi + "bosted/citizen"+id);
  }

  public editCitizen(resident: ResidentDto): Observable<ResidentDto> {
    return this.http.put<ResidentDto>(this.bostedApi + "bosted/citizen", resident);
  }
}
