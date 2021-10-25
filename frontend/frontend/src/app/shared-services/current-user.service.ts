import { Injectable } from '@angular/core';
import {ObservableStore} from "@codewithdan/observable-store";
import {CurrentUserState, EmployeeDto, LoginState} from "../typings";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService extends ObservableStore<CurrentUserState>{

  constructor(private http: HttpClient) {
    super({ logStateChanges: true, trackStateHistory: true });
  }

  public setCurrentUserByUsername(id: string): void {
    this.http.get<EmployeeDto>(`http://localhost:8080/bosted/employees/${id}`).subscribe((employee: EmployeeDto) => {
      this.setState({currentUser: employee});
    });
  }
}
