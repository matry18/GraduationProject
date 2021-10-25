import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ObservableStore} from "@codewithdan/observable-store";
import {CurrentUserState, EmployeeDto, LoginDto, LoginState} from "../typings";
import {BehaviorSubject, Observable} from "rxjs";
import {isElementScrolledOutsideView} from "@angular/cdk/overlay/position/scroll-clip";
import {Router} from "@angular/router";
import {CurrentUserService} from "./current-user.service";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService extends ObservableStore<LoginState> {

  private loginStatus = new BehaviorSubject<boolean>(this.isAuthenticated());
  private username: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(this.getUsernameFromLocalStorage());
  private userRole: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(this.getUserRoleFromLocalStorage());
  private employeeId: string | null = localStorage.getItem("employeeId");
  constructor(private http: HttpClient, private router: Router, private currentUserService: CurrentUserService) {
    super({ logStateChanges: true, trackStateHistory: true });

    this.loginStatus.subscribe((result) => {
      this.setState({ logged_in: result }, 'IS_LOGGED_IN');
    });
  }

  public isAuthenticated(): boolean {
    return this.getUsernameFromLocalStorage() !== null;
  }

  public login(loginDto: LoginDto): any {
    this.http.post<any>("http://localhost:8090/authentication/login", loginDto).subscribe((loginDtoResponse: LoginDto) => {
      if(loginDtoResponse) {
        this.loginStatus.next(true);
        localStorage.setItem("username", loginDtoResponse.username);
        this.currentUserService.setCurrentUserByUsername(loginDtoResponse.employeeId);
        this.username.next(this.getUsernameFromLocalStorage());
        this.router.navigate(["/"]);
        //set roles
        return true;
      } else {
        this.router.navigate(["/login"]);
        return false;
      }
    });
  }

  public logout(): void {
    this.setState({ logged_in: false }, 'IS_LOGGED_IN');
    this.username.next(null);
    this.clearLocalStorage();
    this.router.navigate(["/login"]);
  }

  public redirectIfNotAuthenticated(): void {
    if(!this.isAuthenticated()) {
      this.router.navigate(["/login"]);
    }
  }


  private clearLocalStorage(): void {
   localStorage.clear();

    /* localStorage.removeItem("username");
    localStorage.removeItem("userRole"); */
  }

  public getUsername(): Observable<string | null> {
    return this.username.asObservable();
  }

  public getUserRole(): Observable<string | null> {
    return this.userRole.asObservable();
  }


  private getUsernameFromLocalStorage(): string | null {
    return localStorage.getItem("username");
  }

  private getUserRoleFromLocalStorage(): string | null {
    return localStorage.getItem("userRole");
  }

}
