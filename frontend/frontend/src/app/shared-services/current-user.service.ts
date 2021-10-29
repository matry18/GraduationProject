import {Injectable} from '@angular/core';
import {ObservableStore} from "@codewithdan/observable-store";
import {CurrentUserState, EmployeeDto, LoginState} from "../typings";
import {HttpClient} from "@angular/common/http";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CurrentUserService extends ObservableStore<CurrentUserState> {

  public currentUser = new BehaviorSubject<any | EmployeeDto>(this.getCurrentUserFromLocalStorage());
  constructor(private http: HttpClient) {
    super({logStateChanges: true, trackStateHistory: true});

    this.currentUser.subscribe((currentUser) => {
      this.setState({currentUser: currentUser}, "CURRENT_USER_SET")
    });
  }

  public setCurrentUserByEmployeeId(id: string): void {
    this.http.get<EmployeeDto>(`http://localhost:8080/bosted/employees/${id}`).subscribe((employee: EmployeeDto) => {
      this.currentUser.next(employee);
      localStorage.setItem("currentUser", JSON.stringify(employee));
    });
  }

  private getCurrentUserFromLocalStorage(): EmployeeDto {
    return JSON.parse(localStorage.getItem("currentUser")!);
  }

  public getCurrentUser(): Observable<EmployeeDto | null> {
    return this.currentUser.asObservable();
  }

  public removeCurrentUser(): void {
    localStorage.removeItem("currentUser");
  }
}
