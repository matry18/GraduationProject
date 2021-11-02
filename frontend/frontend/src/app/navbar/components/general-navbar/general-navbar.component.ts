import {Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {AuthenticationService} from "../../../shared-services/authentication.service";
import {BehaviorSubject} from "rxjs";
import {AccessRightDto, CurrentUserState, EmployeeDto} from "../../../typings";
import {Router} from "@angular/router";
import {CurrentUserService} from "../../../shared-services/current-user.service";
import {AuthorizationService} from "../../../shared-services/authorization.service";

@Component({
  selector: 'general-navbar',
  templateUrl: './general-navbar.component.html',
  styleUrls: ['./general-navbar.component.css']
})
export class GeneralNavbarComponent implements OnInit {

  public loggedIn = new BehaviorSubject<boolean>(false);
  private currentUserSubject = new BehaviorSubject<any | EmployeeDto>(null);
  public currentUser: any | EmployeeDto;


  constructor(private authenticationService: AuthenticationService,
              private currentUserService: CurrentUserService,
              public accessRightService: AuthorizationService) {
  }

  public ngOnInit(): void {
    this.authenticationService.globalStateChanged.subscribe((state) => {
      this.loggedIn.next(state.logged_in);
    });

    this.loggedIn.subscribe((state) => {
      if (state) {
        this.currentUserService.globalStateChanged.subscribe((state) => {
          this.currentUserSubject.next(state.currentUser);
          this.currentUser = this.currentUserSubject.getValue();

        });
      }
    });
  }

  public logout(): void {
    this.authenticationService.logout();
  }
}
